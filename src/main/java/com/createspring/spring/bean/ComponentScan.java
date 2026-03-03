package com.createspring.spring.bean;

import com.createspring.spring.annotation.Repository;
import com.createspring.spring.annotation.RestController;
import com.createspring.spring.annotation.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * 컴포넌트 스캔
 * 빈 팩토리의 초기화시 실행
 */
public class ComponentScan {

    /**
     * 리플렉션으로 클래스가 빈으로 등록하는 어노테이션을 보유하고 있는지 확인
     * 빈으로 등록되어야할 클래스 파일을 빈 정의에 등록한다.
     *
     * @param basePackage 기본 패키지
     * @return beanDefinition 빈 정의
     */
    public static Set<Class<?>> scanComponent(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException {
        Set<Class<?>> classes = scanPackage(basePackage);
        Set<Class<?>> beanDefinition = new HashSet<>();

        for (Class<?> clazz : classes) {
            System.out.println(clazz.toString() + "빈 삽입 검사");
            if (clazz.isAnnotationPresent(Service.class) ||
                    clazz.isAnnotationPresent(Repository.class) ||
                    clazz.isAnnotationPresent(RestController.class)) {
                beanDefinition.add(clazz);
                System.out.println(clazz + "빈 정의 삽입");
            }
        }
        return beanDefinition;
    }

    /**
     * 클래스로더를 이용하여 모든 클래스를 스캔한다.
     * 모든 하위 패키지를 스캔 하기위해 재귀적으로 스캔한다.
     *
     * @param packageName 패키지 이름
     * @return classes 모든 클래스 목록
     */
    private static Set<Class<?>> scanPackage(String packageName) throws IOException, URISyntaxException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        String path = packageName.replace(".", "/");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.toURI());
            System.out.println("클래스로더 검사중 " + resource + ", " + directory);

            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    classes.addAll(scanPackage(packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().replace(".class", "");
                    classes.add(Class.forName(className));
                }
            }
        }
        return classes;
    }

}
