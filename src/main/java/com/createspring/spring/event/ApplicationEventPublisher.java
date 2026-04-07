package com.createspring.spring.event;

/**
 * 이벤트 발행자
 */
public interface ApplicationEventPublisher {

    void publishEvent(Object o);
}
