package com.createspring.spring.bean.post;

/**
 * 빈 후처리기
 */
public class PostBeanProcessor {
    private final TransactionalProcessor transactionalProcessor; // 합성 (집약)
    private final EventListenerProcessor eventListenerProcessor;

    public PostBeanProcessor(TransactionalProcessor transactionalProcessor, EventListenerProcessor eventListenerProcessor) {
        this.transactionalProcessor = transactionalProcessor;
        this.eventListenerProcessor = eventListenerProcessor;
    }

    /**
     * 후처리기 오케스트레이션 메서드
     */
    public Object processPostBean(Class<?> clazz, Object o) {
        saveEventBean(clazz);
        return getTransactionalProxy(clazz, o);
    }

    /**
     * 트랜잭션 후처리기를 호출하고 프록시를 반환한다.
     */
    public Object getTransactionalProxy(Class<?> clazz, Object o) {
        return transactionalProcessor.getTransactionalProxy(clazz, o);
    }

    /**
     * 이벤트리스너 후처리기에 메타데이터를 전달한다.
     */
    public void saveEventBean(Class<?> clazz) {
        eventListenerProcessor.eventListenerMethod(clazz);
    }
}
