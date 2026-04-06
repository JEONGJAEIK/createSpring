package com.createspring.spring.bean.post;

import com.createspring.spring.annotation.EventListener;
import com.createspring.spring.bean.DefaultSingletonBeanRegistry;
import com.createspring.spring.event.SimpleEventListenerFactory;

import java.lang.reflect.Method;

/**
 * 이벤트리스너 빈 후처리기
 */
public class EventListenerProcessor {

    /**
     * 이벤트리스너 어노테이션이 존재하면 빈 이름을 리스터에 보관한다.
     */
    public static void eventListenerMethod(Class<?> clazz) {
        DefaultSingletonBeanRegistry registry = new DefaultSingletonBeanRegistry();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventListener.class)) {
                if (registry.checkHasBean(clazz)) {
                    String beanName = registry.getBeanName(clazz);
                    SimpleEventListenerFactory.setBeanNameList(beanName);
                }
            }
        }
    }
}
