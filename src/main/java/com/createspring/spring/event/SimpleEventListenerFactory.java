package com.createspring.spring.event;

import java.util.HashMap;
import java.util.Map;

/**
 * 이벤트리스너 저장소
 * 트랜잭셔널 이벤트리스너는 별도의 저장소에서 다룬다.
 */
public class SimpleEventListenerFactory {

    /**
     * 이벤트리스너의 빈 이름을 가지고 있는 해시 맵
     */
    private static Map<Class<?>[], ApplicationListenerMethodAdapter> listenerList = new HashMap<>();

    public static void setListener(Class<?>[] trigger, ApplicationListenerMethodAdapter adapter) {
        listenerList.put(trigger, adapter);
    }

    public static ApplicationListenerMethodAdapter getAdapter(Class<?> clazz) {
        return listenerList.get(clazz);
    }


}
