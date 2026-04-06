package com.createspring.spring.bean.post;

/**
 * 빈 후처리기
 */
public class PostBeanProcessor {

    /**
     * 객체를 프록시팩토리로 전달하고 프록시를 반환한다.
     */
    public static Object scanTargetProxy(Class<?> clazz, Object o) {
        return TransactionalProcessor.getTransactionalProxy(clazz, o);
    }
}
