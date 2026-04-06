package com.createspring.spring.bean;

public interface ApplicationContext {

    Object getBean(String beanName);

    String getClassName(String beanName);
}
