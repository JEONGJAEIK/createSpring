package com.createspring.spring.bean;

import com.createspring.spring.bean.post.EventListenerProcessor;
import com.createspring.spring.bean.post.PostBeanProcessor;
import com.createspring.spring.bean.post.TransactionalProcessor;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Set;

/**
 * 빈 팩토리
 */
public class BeanFactory extends DefaultSingletonBeanRegistry {

    private static final BeanFactory singletonBeanFactory = new BeanFactory();
    private final PostBeanProcessor postBeanProcessor;

    // 합성
    private BeanFactory() {
        this.postBeanProcessor = new PostBeanProcessor(new TransactionalProcessor(), new EventListenerProcessor());
    }

    public static BeanFactory getBeanFactory() {
        return singletonBeanFactory;
    }

    /**
     * 빈 팩토리를 초기화한다. 톰캣이 실행되기 전에 미리 실행한다.
     * 컴포넌트 스캔을 실행하고 메타데이터로 객체 생성과 의존관계 주입을 실행한다.
     */
    public void initialize(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Set<Class<?>> metaDataSet = ComponentScan.scanComponent(basePackage);
        for (Class<?> clazz : metaDataSet) {
            dependencyInject(clazz);
        }
    }

    /**
     * 의존관계 주입
     * 이미 객체가 생성되어 있으면 건너뛴다.
     * 리플렉션을 이용하여 클래스의 생성자와 매개변수를 가져온다. 재귀적으로 실행한다.
     * boardController -> boardService -> boardRepository 순으로 DFS로 실행한다.
     * 리플렉션을 이용하여 객체를 생성하고 빈에 삽입한다.
     * 생성 직후 빈 후처리기를 적용하여 프록시가 필요한 빈은 프록시로 교체한다.
     */
    public <T> T dependencyInject(Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (typeToNameMap.containsKey(clazz)) {
            String beanName = typeToNameMap.get(clazz);
            return clazz.cast(singletonMap.get(beanName));
        }

        Constructor<?> constructor = clazz.getConstructors()[0];
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] dependencies = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            dependencies[i] = dependencyInject(paramTypes[i]);
            System.out.println(dependencies[i] + "의존관계 주입 완료");
        }

        Object instance = constructor.newInstance(dependencies);
        Object processed = postBeanProcessor.processPostBean(clazz, instance);
        BeanDefinition beanDefinition = new BeanDefinition(clazz);
        setBeanMap(processed, beanDefinition);
        System.out.println(clazz.getSimpleName() + "빈 생성 완료");
        return clazz.cast(processed);
    }
}