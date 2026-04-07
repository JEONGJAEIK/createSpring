package com.createspring.spring.bean.post;

import com.createspring.board.event.PostCreateEvent;
import com.createspring.board.event.PostEventListener;
import com.createspring.board.event.PostSearchEvent;
import com.createspring.spring.event.ApplicationListenerMethodAdapter;
import com.createspring.spring.event.SimpleEventListenerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EventListenerProcessorTest {

    @Test
    void 리스너_빈후처리기_어댑터_저장시_파라미터_일치 () {
        EventListenerProcessor eventListenerProcessor = new EventListenerProcessor();
        eventListenerProcessor.eventListenerMethod(PostEventListener.class);
        SimpleEventListenerFactory simpleEventListenerFactory = new SimpleEventListenerFactory();
        List<ApplicationListenerMethodAdapter> cAdapter = simpleEventListenerFactory.getAdapter(PostCreateEvent.class);
        List<ApplicationListenerMethodAdapter> sAdapter = simpleEventListenerFactory.getAdapter(PostSearchEvent.class);

        Assertions.assertEquals(1, cAdapter.size());
        Assertions.assertEquals(1, sAdapter.size());
        Assertions.assertEquals("postEventListener", cAdapter.getFirst().getBeanName());
        Assertions.assertEquals("hello", cAdapter.getFirst().getMethod().getName());
        Assertions.assertEquals("postEventListener", sAdapter.getFirst().getBeanName());
        Assertions.assertEquals("hello2", sAdapter.getFirst().getMethod().getName());

        System.out.println("cAdapter 크기 = " + cAdapter.size());
        System.out.println("sAdapter 크기 = " + sAdapter.size());
        System.out.println("sAdapter 메서드 이름 = " + sAdapter.getFirst().getMethod().getName());
        System.out.println("cAdapter 메서드 이름 = " + cAdapter.getFirst().getMethod().getName());
        System.out.println("sAdapter 빈 이름 = " + sAdapter.getFirst().getBeanName());
        System.out.println("cAdapter 빈 이름 = " + cAdapter.getFirst().getBeanName());
    }
}