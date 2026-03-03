package com.createspring.spring.proxy;

import com.createspring.ConnectionUtil;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 트랜잭셔널 인터셉터
 */
public class TransactionalInterceptor implements MethodInterceptor {
    private final Object object;

    public TransactionalInterceptor(Object object) {
        this.object = object;
    }

    /**
     * 커밋과 롤백의 부가사항을 삽입한다.
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        ConnectionUtil.begin();
        try {
            Object result = methodProxy.invoke(object, objects);
            ConnectionUtil.commit();
            return result;
        } catch (Exception e) {
            ConnectionUtil.rollback();
            throw e;
        } finally {
            ConnectionUtil.close();
        }
    }
}
