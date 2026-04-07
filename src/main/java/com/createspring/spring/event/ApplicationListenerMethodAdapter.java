package com.createspring.spring.event;

import java.lang.reflect.Method;

/**
 * 이벤트리스너 래핑 어댑터
 */
public class ApplicationListenerMethodAdapter {

    /**
     * 리스너 객체의 빈 네임
     */
    private String beanName;

    /**
     * 리스너가 실행해야할 메서드
     */
    private Method method;

    public ApplicationListenerMethodAdapter(String beanName, Method method) {
        this.beanName = beanName;
        this.method = method;
    }

    public String getBeanName() {
        return beanName;
    }

    public Method getMethod() {
        return method;
    }
}
