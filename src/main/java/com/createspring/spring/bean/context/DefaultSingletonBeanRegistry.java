package com.createspring.spring.bean.context;

import com.createspring.spring.bean.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * 빈 저장소 구현체
 */
public class DefaultSingletonBeanRegistry {

    /**
     * 빈 이름, 빈 객체 맵
     */
    protected Map<String, Object> singletonMap = new HashMap<>();

    /**
     * 빈 이름, 빈 정의 맵
     */
    protected Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * 클래스, 빈 이름 맵, DI과정 중 필요
     */
    protected Map<Class<?>, String> typeToNameMap = new HashMap<>();

    /**
     * 빈을 맵에 저장한다.
     */
    public void setBeanMap(Object singleton, BeanDefinition beanDefinition) {
        String beanName = createBeanName(beanDefinition.getBeanClass());
        Class<?> beanClass = beanDefinition.getBeanClass();
        beanDefinitionMap.put(beanName, beanDefinition);
        singletonMap.put(beanName, singleton);
        typeToNameMap.put(beanClass, beanName);
    }

    /**
     * 빈 이름을 생성한다 첫 글자를 소문자로
     */
    private String createBeanName(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
    }

    /**
     * 빈 이름으로 빈 객체를 반환한다.
     */
    public Object getBean(String beanName) {
        return singletonMap.get(beanName);
    }

    /**
     * 메타데이터로 빈 이름을 반환한다.
     */
    public String getBeanName(Class<?> clazz) {
        return typeToNameMap.get(clazz);
    }

    /**
     * 메타데이터로 빈이 존재하는지 확인한다.
     */
    public boolean checkHasBean(Class<?> clazz) {
        return typeToNameMap.containsKey(clazz);
    }

    /**
     * 인터페이스 타입을 구현체 빈 이름으로 매핑한다.
     */
    public void registerTypeMapping(Class<?> interfaceType, Class<?> implType) {
        String beanName = typeToNameMap.get(implType);
        typeToNameMap.put(interfaceType, beanName);
    }
}
