package com.createspring.spring.bean.post;

/**
 * 빈 후처리기
 */
public class PostBeanProcessor {

    /**
     * 후처리기 오케스트레이션 메서드
     */
    public static Object processPostBean(Class<?> clazz, Object o) {
        saveEventBean(clazz);
        return getTransactionalProxy(clazz, o);
    }

    /**
     * 트랜잭션 후처리기를 호출하고 프록시를 반환한다.
     */
    public static Object getTransactionalProxy(Class<?> clazz, Object o) {
        return TransactionalProcessor.getTransactionalProxy(clazz, o);
    }

    /**
     * 이벤트리스너 후처리기에 메타데이터를 전달한다.
     */
    public static void saveEventBean(Class<?> clazz) {
        EventListenerProcessor.eventListenerMethod(clazz);
    }
}
