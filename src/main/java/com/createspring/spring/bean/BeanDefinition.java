package com.createspring.spring.bean;

/**
 * 빈 정의
 * 클래스 메타데이터를 관리한다.
 */
public class BeanDefinition {

    private Class<?> beanClass;

    private String beanClassName;

    public BeanDefinition (Class<?> metaData) {
        this.beanClass = metaData;
        this.beanClassName = createBeanName(metaData);
    }

    private String createBeanName(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }
}
