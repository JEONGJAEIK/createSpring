package com.createspring.spring.bean.post;

import com.createspring.spring.annotation.EventListener;
import com.createspring.spring.annotation.Transactional;
import com.createspring.spring.proxy.ProxyFactory;

import java.lang.reflect.Method;

public class EventListenerProcesser {

    /**
     * 이벤트리스너를 조사하고 레지스트리에 보관한다.
     */
    private void eventListenerMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventListener.class)) {
                
            }
        }
    }
}
