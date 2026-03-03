package com.createspring.spring.proxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * 프록시 팩토리
 */
public class ProxyFactory {

    /**
     * 트랜잭셔널이 있는 클래스를 프록시화 시킨다.
     */
    public Object handleInterceptor(Object o) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(o.getClass());
        enhancer.setCallback(new TransactionalInterceptor(o));
        return enhancer.create();
    }
}
