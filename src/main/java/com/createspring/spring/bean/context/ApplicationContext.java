package com.createspring.spring.bean.context;


/**
 * 사용자가 접근하는 빈 저장소의 추상화 인터페이스
 * {@link AbstractApplicationContext} 에서 BeanFactory를 합성하여 위임한다.
 * BeanFactory보다 더 많은 기능을 가지고 있다.
 */
public interface ApplicationContext {

    /**
     * 빈 객체 반환
     */
    Object getBean(String beanName);

    /**
     * 빈 저장소 초기화
     */
    void initialize(String basePackage) throws Exception;

    /**
     * 빈 이름 반환
     */
    String getBeanName(Class<?> clazz);
}
