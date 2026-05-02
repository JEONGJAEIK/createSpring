package com.createspring.spring.proxy;

import com.createspring.spring.transaction.AbstractPlatformTransactionManager;
import com.createspring.spring.transaction.TransactionalInterceptor;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

/**
 * 프록시 팩토리
 */
public class ProxyFactory {
    private static final Objenesis objenesis = new ObjenesisStd();

    /**
     * 트랜잭셔널이 있는 클래스를 프록시화 시킨다.
     * Objenesis를 사용하여 기본 생성자 없이도 프록시 인스턴스를 생성한다.
     */
    public static Object handleInterceptor(Object o, AbstractPlatformTransactionManager txManager) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(o.getClass());
        enhancer.setCallbackType(TransactionalInterceptor.class);
        Class<?> proxyClass = enhancer.createClass();
        Object proxy = objenesis.newInstance(proxyClass);
        ((Factory) proxy).setCallbacks(new Callback[]{new TransactionalInterceptor(o, txManager)});
        return proxy;
    }
}
