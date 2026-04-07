package com.createspring;

import com.createspring.board.controller.PostCreateController;
import com.createspring.board.controller.PostSearchController;
import com.createspring.spring.bean.ApplicationContext;
import com.createspring.spring.bean.BeanFactory;
import com.createspring.spring.bean.DefaultSingletonBeanRegistry;
import jakarta.servlet.Servlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

/**
 * 메인 클래스
 */
public class Main {

    /**
     * 톰캣 시작전에 스프링의 빈 팩토리를 초기화하고 서블릿에는 빈의 객체를 전달한다.
     * 모든 객체는 싱글톤이다.
     */
    public static void main(String[] args) throws LifecycleException, IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.initialize("com.createspring");
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context context = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(context, "postSearchController", (Servlet) beanFactory.getBean("postSearchController"));
        context.addServletMappingDecoded("/post/search", "postSearchController");

        Tomcat.addServlet(context, "postCreateController", (Servlet) beanFactory.getBean("postCreateController"));
        context.addServletMappingDecoded("/post/create", "postCreateController");

        tomcat.start();
        tomcat.getServer().await();
    }
}
