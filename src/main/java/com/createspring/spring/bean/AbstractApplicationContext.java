package com.createspring.spring.bean;

import com.createspring.spring.event.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 애플리케이션 컨텍스트를 구현한다.
 * 빈 저장소를 집약하여 빈 반환등의 기본 로직들을 추상화 시킴
 */
public class AbstractApplicationContext implements ApplicationContext, ApplicationEventPublisher {
    private final BeanFactory registry;
    private final SimpleEventListenerFactory factory;
    private final TransactionalEventListenerFactory txFactory;

    public AbstractApplicationContext(BeanFactory registry, SimpleEventListenerFactory factory, TransactionalEventListenerFactory txFactory) {
        this.registry = registry;
        this.factory = factory;
        this.txFactory = txFactory;
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
        List<ApplicationListenerMethodAdapter> adapterList = factory.getAdapter(clazz);
        List<TransactionListenerMethodAdapter> txAdapterList = txFactory.getAdapter(clazz);
        if (adapterList == null) {
            processTransactionEventFactory(txAdapterList, o);
        } else {
            processSimpleEventFactory(adapterList, o);
        }
    }

    /**
     * 기본 이벤트리스너를 실행한다.
     */
    private void processSimpleEventFactory(List<ApplicationListenerMethodAdapter> adapterList, Object o) {
        for (ApplicationListenerMethodAdapter adapter : adapterList) {
            String beanName = adapter.getBeanName();
            Method method = adapter.getMethod();
            Object bean = getBean(beanName);
            try {
                method.invoke(bean, o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 트랜잭셔널 이벤트리스너를 실행한다.
     */
    //TODO 트랜잭션동기화매니저를 구현하고 콜백 등록 로직 구현 필요
    private void processTransactionEventFactory(List<TransactionListenerMethodAdapter> txAdapterList, Object o) {
        for (TransactionListenerMethodAdapter txAdapter : txAdapterList) {
            String beanName = txAdapter.getBeanName();
            Method method = txAdapter.getMethod();
            Object bean = getBean(beanName);
            try {
                method.invoke(bean, o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
