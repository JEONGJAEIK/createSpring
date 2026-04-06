package com.createspring.spring.bean;

import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements ApplicationContext {

    protected static Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    protected static Map<String, Object> singletonMap = new HashMap<>();

    protected static Map<Class<?>, String> typeToNameMap = new HashMap<>();

    public static void setBeanDefinitionMap(Object singleton, BeanDefinition beanDefinition) {
        String beanName = beanDefinition.getBeanClassName();
        Class<?> beanClass = beanDefinition.getBeanClass();
        beanDefinitionMap.put(beanName, beanDefinition);
        singletonMap.put(beanName, singleton);
        typeToNameMap.put(beanClass, beanName);
    }

    /**
     * 클래스 이름으로 빈 객체를 반환한다.
     */
    @Override
    public Object getBean(String beanName) {
        return singletonMap.get(beanName);
    }

    @Override
    public String getClassName(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        return beanDefinition.getBeanClassName();
    }
}
