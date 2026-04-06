package com.createspring.spring.bean;

import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements ApplicationContext{

    private static Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    private static Map<String, Object> singletonMap = new HashMap<>();

    public static void setBeanDefinitionMap(Object singleton, BeanDefinition beanDefinition) {
        String beanName = beanDefinition.getBeanClassName();
        beanDefinitionMap.put(beanName, beanDefinition);
        singletonMap.put(beanName, singleton);
    }

    /**
     * 클래스 메타데이터로 빈 객체를 반환한다.
     */
    @Override
    public Object getBean(String beanName) {
        return singletonMap.get(beanName);
    }
}
