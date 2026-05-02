package com.createspring.spring.transaction;

import com.createspring.spring.annotation.Transactional;
import com.createspring.spring.jdbc.DataSourceTransactionManager;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


import java.lang.reflect.Method;

/**
 * 트랜잭셔널 인터셉터
 */
public class TransactionalInterceptor implements MethodInterceptor {
    private final AbstractPlatformTransactionManager platformTransactionManager;
    private final Object object;

    public TransactionalInterceptor(Object object, AbstractPlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
        this.object = object;
    }

    /**
     * 커밋과 롤백의 부가사항을 삽입한다. 트랜잭셔널이 있는 메서드만 부가로직 실행
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (method.isAnnotationPresent(Transactional.class)) {
            platformTransactionManager.getTransaction();
            try {
                Object result = methodProxy.invoke(object, objects);
                platformTransactionManager.commit();
                return result;
            } catch (Exception e) {
                platformTransactionManager.rollback();
                throw e;
            } finally {
                platformTransactionManager.close();
            }
        }
        return methodProxy.invoke(object, objects);
    }
}
