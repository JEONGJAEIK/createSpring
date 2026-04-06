package com.createspring.spring.bean;

/**
 * 빈 정의
 * 빈이 되어야할 클래스 메타데이터를 관리한다.
 */
public class BeanDefinition {

    private Class<?> beanClass;

    private String beanClassName;

    public BeanDefinition (Class<?> metaData) {
        this.beanClass = metaData;
        this.beanClassName = createBeanName(metaData);
    }

    private String createBeanName(Class<?> metaData) {
        return metaData.getSimpleName();
    }

    public String getBeanClassName() {
        return beanClassName;
    }
}
