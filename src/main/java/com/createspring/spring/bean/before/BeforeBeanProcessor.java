package com.createspring.spring.bean.before;

import com.createspring.spring.bean.AbstractApplicationContext;
import com.createspring.spring.bean.BeanDefinition;
import com.createspring.spring.bean.BeanFactory;
import com.createspring.spring.bean.DefaultSingletonBeanRegistry;
import com.createspring.spring.event.ApplicationEventPublisher;
import com.createspring.spring.event.SimpleEventListenerFactory;

/**
 * 빈 전처리기
 * 사용자 정의 빈을 등록하기 전에 필수 빈들을 등록한다.
 */
public class BeforeBeanProcessor {

    /**
     * 애플리케이션 컨텍스트를 미리 빈으로 등록한다.
     */
    public static void process(DefaultSingletonBeanRegistry registry) {
        AbstractApplicationContext applicationContext = new AbstractApplicationContext((BeanFactory) registry, new SimpleEventListenerFactory());
        registry.setBeanMap(applicationContext, new BeanDefinition(AbstractApplicationContext.class));
        registry.registerTypeMapping(ApplicationEventPublisher.class, AbstractApplicationContext.class);
    }
}
