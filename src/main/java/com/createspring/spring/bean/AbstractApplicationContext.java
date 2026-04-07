package com.createspring.spring.bean;

import com.createspring.spring.event.ApplicationEventPublisher;
import com.createspring.spring.event.ApplicationListenerMethodAdapter;
import com.createspring.spring.event.SimpleEventListenerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AbstractApplicationContext implements ApplicationContext, ApplicationEventPublisher {
    private final DefaultSingletonBeanRegistry registry;
    private final SimpleEventListenerFactory factory;

    public AbstractApplicationContext(DefaultSingletonBeanRegistry registry, SimpleEventListenerFactory factory) {
        this.registry = registry;
        this.factory = factory;
    }

    @Override
    public Object getBean(String beanName) {
        return registry.singletonMap.get(beanName);
    }

    @Override
    public String getBeanName(Class<?> clazz) {
        return registry.typeToNameMap.get(clazz);
    }

    @Override
    public void publishEvent(Object o) {
        Class<?> clazz = o.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        ApplicationListenerMethodAdapter adapter = SimpleEventListenerFactory.getAdapter(clazz);
        String beanName = adapter.getBeanName();
        Method method = adapter.getMethod();
        Object bean = getBean(beanName);
        try {
            method.invoke(bean, (Object) declaredFields);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
