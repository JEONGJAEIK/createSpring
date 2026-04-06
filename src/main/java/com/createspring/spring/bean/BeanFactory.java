package com.createspring.spring.bean;

import com.createspring.spring.bean.post.PostBeanProcessor;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 빈 팩토리
 */
public class BeanFactory {
    private static final Map<Class<?>, Object> immediateMap = new HashMap<>();

    /**
     * 빈 팩토리를 초기화한다. 톰캣이 실행되기 전에 미리 실행한다.
     * 빈 정의리스트를 가지고 와서 객체 생성과 의존관계 주입을 실행한다.
     * 완료되면 빈 후처리기에 빈을 전달한다.
     */
    public static void initialize(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Set<Class<?>> metaDataSet = initBeanDefinition(basePackage);
        for (Class<?> clazz : metaDataSet) {
            dependencyInject(clazz);
        }
    }

    /**
     * 빈 팩토리의 초기화시 발생한다. 컴포넌트 스캔으로 부터 얻은 빈정의를 저장하고 빈 팩토리에 전달한다.
     *
     */
    public static Set<Class<?>> initBeanDefinition(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException {
        return ComponentScan.scanComponent(basePackage);
    }

    /**
     * 의존관계 주입
     * 이미 객체가 생성되어 있으면 건너뛴다.
     * 리플렉션을 이용하여 클래스의 생성자와 매개변수를 가져온다. 재귀적으로 실행한다.
     * boardController -> boardService -> boardRepository 순으로 DFS로 실행한다.
     * 리플렉션을 이용하여 객체를 생성하고 빈에 삽입한다.
     * 생성 직후 빈 후처리기를 적용하여 프록시가 필요한 빈은 프록시로 교체한다.
     */
    public static <T> T dependencyInject(Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (immediateMap.containsKey(clazz)) {
            return clazz.cast(immediateMap.get(clazz));
        }

        Constructor<?> constructor = clazz.getConstructors()[0];
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] dependencies = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            dependencies[i] = dependencyInject(paramTypes[i]);
            System.out.println(dependencies[i] + "의존관계 주입 완료");
        }

        Object instance = constructor.newInstance(dependencies);
        Object processed = PostBeanProcessor.scanTargetProxy(clazz, instance);
        BeanDefinition beanDefinition = new BeanDefinition(clazz);
        DefaultSingletonBeanRegistry.setBeanDefinitionMap(processed, beanDefinition);
        System.out.println(beanDefinition.getBeanClassName() + "빈 생성 완료");
        return clazz.cast(processed);
    }
}