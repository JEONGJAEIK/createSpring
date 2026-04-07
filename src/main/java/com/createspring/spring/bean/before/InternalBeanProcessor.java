package com.createspring.spring.bean.before;

import com.createspring.spring.bean.AbstractApplicationContext;
import com.createspring.spring.bean.BeanDefinition;
import com.createspring.spring.bean.BeanFactory;
import com.createspring.spring.bean.ComponentScan;
import com.createspring.spring.bean.DefaultSingletonBeanRegistry;
import com.createspring.spring.event.ApplicationEventPublisher;
import com.createspring.spring.event.SimpleEventListenerFactory;
import com.createspring.spring.jdbc.DataSource;
import com.createspring.spring.jdbc.DataSourceTransactionManager;

import java.util.Set;

/**
 * 내부 빈 등록기
 * 사용자 정의 빈을 등록하기 전에 필수 빈들을 등록한다.
 */
public class InternalBeanProcessor {

    /**
     * 필수 빈들을 미리 등록한다.
     */
    public static void process(DefaultSingletonBeanRegistry registry, String basePackage) throws Exception {
        createContext(registry);
        createTxManager(registry, basePackage);
    }

    /**
     * 애플리케이션 컨텍스트를 미리 빈으로 등록한다.
     */
    public static void createContext(DefaultSingletonBeanRegistry registry) {
        AbstractApplicationContext applicationContext = new AbstractApplicationContext((BeanFactory) registry, new SimpleEventListenerFactory());
        registry.setBeanMap(applicationContext, new BeanDefinition(AbstractApplicationContext.class));
        registry.registerTypeMapping(ApplicationEventPublisher.class, AbstractApplicationContext.class);
    }

    /**
     * 리플렉션으로 DataSource 구현체를 찾아 빈으로 등록하고 트랜잭션 매니저에 주입한다.
     */
    //TODO 현재 컴포넌트 스캔을 2번하는 문제가 있음 컴포넌트 스캔의 단계를 나눌 필요성
    public static void createTxManager(DefaultSingletonBeanRegistry registry, String basePackage) throws Exception {
        Set<Class<?>> classes = ComponentScan.scanPackage(basePackage);
        DataSource dataSource = findAndCreateDataSource(classes);
        registry.setBeanMap(dataSource, new BeanDefinition(dataSource.getClass()));
        registry.registerTypeMapping(DataSource.class, dataSource.getClass());

        DataSourceTransactionManager txManager = new DataSourceTransactionManager(dataSource);
        registry.setBeanMap(txManager, new BeanDefinition(DataSourceTransactionManager.class));
    }

    /**
     * 스캔된 클래스 중 DataSource 구현체를 찾아 인스턴스를 생성한다.
     */
    private static DataSource findAndCreateDataSource(Set<Class<?>> classes) throws Exception {
        for (Class<?> clazz : classes) {
            if (DataSource.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
                return (DataSource) clazz.getConstructors()[0].newInstance();
            }
        }
        throw new IllegalStateException("DataSource 구현체를 찾을 수 없습니다. DataSource를 구현한 @Component 클래스를 등록해주세요.");
    }
}
