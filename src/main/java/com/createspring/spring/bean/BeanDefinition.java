package com.createspring.spring.bean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

/**
 * 빈 정의
 * 빈이 되어야할 클래스 메타데이터를 관리한다.
 */
public class BeanDefinition {
    private static Set<Class<?>> beanDefinition = new HashSet<>();

    /**
     * 빈 팩토리의 초기화시 발생한다. 컴포넌트 스캔으로 부터 얻은 빈정의를 저장하고 빈 팩토리에 전달한다.
     *
     */
    public static Set<Class<?>> initBeanDefinition(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException {
        beanDefinition = ComponentScan.scanComponent(basePackage);
        return beanDefinition;
    }
}
