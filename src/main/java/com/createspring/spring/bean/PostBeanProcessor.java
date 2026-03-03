package com.createspring.spring.bean;

import com.createspring.spring.annotation.Transactional;
import com.createspring.spring.proxy.ProxyFactory;

/**
 * 빈 후처리기
 */
public class PostBeanProcessor {
    private final ProxyFactory proxyFactory;

    public PostBeanProcessor(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    public Object scanTargetProxy(Object o, Class<?> clazz) {
        if (clazz.isAnnotationPresent(Transactional.class)) {
            return proxyFactory.handleInterceptor(o);
        }
        return o;
    }
}
