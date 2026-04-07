package com.createspring.spring.bean.post;

import com.createspring.spring.annotation.Transactional;
import com.createspring.spring.proxy.ProxyFactory;

import java.lang.reflect.Method;

public class TransactionalProcessor {

    /**
     * 트랜잭셔널이 달린 클래스의 프록시를 반환한다.
     */
    public Object getTransactionalProxy(Class<?> clazz, Object o) {
        if (clazz.isAnnotationPresent(Transactional.class) || hasTransactionalMethod(clazz)) {
            return ProxyFactory.handleInterceptor(o);
        }
        return o;
    }

    /**
     * 트랜잭셔널이 달린 메서드를 판별한다.
     */
    private boolean hasTransactionalMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Transactional.class)) {
                return true;
            }
        }
        return false;
    }
}
