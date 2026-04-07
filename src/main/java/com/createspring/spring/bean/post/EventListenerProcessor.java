package com.createspring.spring.bean.post;

import com.createspring.spring.annotation.EventListener;
import com.createspring.spring.event.ApplicationListenerMethodAdapter;
import com.createspring.spring.event.SimpleEventListenerFactory;

import java.lang.reflect.Method;

/**
 * 이벤트리스너 빈 후처리기
 */
public class EventListenerProcessor {

    /**
     * 이벤트리스너 어노테이션이 존재하면 리스너 메타데이터를 등록한다.
     * dependencyInject 안에서만 호출되므로 clazz는 항상 빈 대상 클래스다.
     */
    public void eventListenerMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventListener.class)) {
                String beanName = toBeanName(clazz);
                Class<?>[] triggerEvent = method.getParameterTypes();
                ApplicationListenerMethodAdapter adapter = new ApplicationListenerMethodAdapter(beanName, method);
                SimpleEventListenerFactory.setListener(triggerEvent[0], adapter);
            }
        }
    }

    private String toBeanName(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
    }
}
