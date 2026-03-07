package com.createspring.spring.event;

import com.createspring.spring.annotation.EventListener;
import com.createspring.spring.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EventListenerMethodProcessor {

    /**
     * 빈 후처리기 까지 완전히 다 끝난 다음 시행
     */
    public static void afterSingletonsInstantiated(Set<Class<?>> classes) {
        // 이벤트리스너가 있는 메서드, 이벤트리스너
        Map<Method, EventListener> annotatedMethods = new HashMap<>();

        // 클래스나 메서드에 이벤트리스너가 있는경우 해당 메서드와 이벤트리스너를 기록
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(EventListener.class)) {
                Method[] declaredMethods = clazz.getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    Method method = hasEventListenerMethod(declaredMethod);
                    if (method != null) {
                        EventListener annotation = method.getAnnotation(EventListener.class);
                        annotatedMethods.put(method, annotation);
                    }
                }
            }
        }


    }

    private static Method hasEventListenerMethod(Method method) {
        if (method.isAnnotationPresent(EventListener.class)) {
            return method;
        }
        return null;
    }
}
