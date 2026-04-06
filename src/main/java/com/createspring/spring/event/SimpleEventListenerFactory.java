package com.createspring.spring.event;

import java.util.ArrayList;
import java.util.List;

/**
 * 이벤트리스너 저장소
 * 트랜잭셔널 이벤트리스너는 별도의 저장소에서 다룬다.
 */
public class SimpleEventListenerFactory {

    /**
     * 이벤트리스너의 빈 이름을 가지고 있는 리스트
     */
    private static List<String> beanNameList = new ArrayList<>();

    public static void setBeanNameList(String beanName) {
        beanNameList.add(beanName);
    }
}
