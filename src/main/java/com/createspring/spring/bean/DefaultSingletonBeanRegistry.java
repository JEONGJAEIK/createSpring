package com.createspring.spring.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 빈 저장소 구현체
 */
public class DefaultSingletonBeanRegistry implements ApplicationContext {

    /**
     * 빈 이름, 빈 객체 맵
     */
    protected Map<String, Object> singletonMap = new HashMap<>();

    /**
     * 빈 이름, 빈 정의 맵
     */
    protected Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * 클래스, 빈 이름 맵, DI과정 중 필요
     */
    protected Map<Class<?>, String> typeToNameMap = new HashMap<>();

    public void setBeanDefinitionMap(Object singleton, BeanDefinition beanDefinition) {
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
