package com.createspring.spring.bean;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 빈 팩토리
 * 빈을 관리한다.
 */
public class BeanFactory {
    private static Map<Class<?>, Object> beans = new HashMap<>();

    /**
     * 빈 팩토리를 초기화한다. 톰캣이 실행되기 전에 미리 실행한다.
     * 빈 정의리스트를 가지고 와서 객체 생성과 의존관계 주입을 실행한다.
     */
    public static void initialize(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Set<Class<?>> beanDefinition = BeanDefinition.initBeanDefinition(basePackage);

        for (Class<?> clazz : beanDefinition) {
            dependencyInject(clazz);
        }
    }

    /**
     * 의존관계 주입
     * 이미 객체가 생성되어 있으면 건너뛴다.
     * 리플렉션을 이용하여 클래스의 생성자와 매개변수를 가져온다. 재귀적으로 실행한다.
     * boardController -> boardService -> boardRepository 순으로 DFS로 실행한다.
     * 리플렉션을 이용하여 객체를 생성하고 빈에 삽입한다.
     */
    public static <T> T dependencyInject(Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (beans.containsKey(clazz)) {
            return clazz.cast(beans.get(clazz));
        }

        Constructor<?> constructor = clazz.getConstructors()[0];
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] dependencies = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            dependencies[i] = dependencyInject(paramTypes[i]);
            System.out.println(dependencies[i] + "의존관계 주입 완료");
        }

        Object instance = constructor.newInstance(dependencies);
        beans.put(clazz, instance);
        System.out.println(clazz + "빈 생성 완료");
        return clazz.cast(instance);
    }

    /**
     * 클래스 메타데이터로 빈 객체를 반환한다.
     */
    public static <T> T getBean(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }
}