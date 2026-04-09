package com.createspring.spring.bean;

import com.createspring.spring.event.*;
import com.createspring.spring.transaction.TransactionPhase;
import com.createspring.spring.transaction.TransactionSynchronizationManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 애플리케이션 컨텍스트를 구현한다.
 * 빈 저장소를 집약하여 빈 반환등의 기본 로직들을 추상화 시킴
 */
public class AbstractApplicationContext implements ApplicationContext, ApplicationEventPublisher {
    private final BeanFactory beanFactory = new BeanFactory();
    private final SimpleEventListenerFactory factory = new SimpleEventListenerFactory();
    private final TransactionalEventListenerFactory txFactory = new TransactionalEventListenerFactory();

    public AbstractApplicationContext() {
        beanFactory.setBeanMap(this, new BeanDefinition(AbstractApplicationContext.class));
        beanFactory.registerTypeMapping(ApplicationEventPublisher.class, AbstractApplicationContext.class);
    }

    @Override
    public void initialize(String basePackage) throws Exception {
        beanFactory.initialize(basePackage);
    }

    @Override
    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }

    @Override
    public String getBeanName(Class<?> clazz) {
        return beanFactory.getBeanName(clazz);
    }

    @Override
    public void publishEvent(Object o) {
        Class<?> clazz = o.getClass();
        List<ApplicationListenerMethodAdapter> adapterList = factory.getAdapter(clazz);
        List<TransactionListenerMethodAdapter> txAdapterList = txFactory.getAdapter(clazz);
        if (adapterList != null) {
            processSimpleEventFactory(adapterList, o);
        }
        if (txAdapterList != null) {
            processTransactionEventFactory(txAdapterList, o);
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
     * 트랜잭셔널 이벤트리스너를 TransactionSynchronizationManager에 콜백으로 등록한다.
     * 트랜잭션 커밋 후 실행된다.
     */
    private void processTransactionEventFactory(List<TransactionListenerMethodAdapter> txAdapterList, Object o) {
        for (TransactionListenerMethodAdapter txAdapter : txAdapterList) {
            String beanName = txAdapter.getBeanName();
            Method method = txAdapter.getMethod();
            Object bean = getBean(beanName);
            TransactionPhase phase = txAdapter.getPhase();
            if (phase == TransactionPhase.AFTER_COMMIT) {
                TransactionSynchronizationManager.registerSynchronization(() -> {
                    try {
                        method.invoke(bean, o);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }
}
