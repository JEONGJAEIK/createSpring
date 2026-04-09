package com.createspring;

import com.createspring.spring.bean.context.AbstractApplicationContext;
import com.createspring.spring.bean.context.ApplicationContext;
import jakarta.servlet.Servlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

/**
 * 메인 클래스
 */
public class Main {

    /**
     * 톰캣 시작전에 스프링의 빈 팩토리를 초기화하고 서블릿에는 빈의 객체를 전달한다.
     * 모든 객체는 싱글톤이다.
     */
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new AbstractApplicationContext();
        applicationContext.initialize("com.createspring");
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context context = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(context, "postSearchController", (Servlet) applicationContext.getBean("postSearchController"));
        context.addServletMappingDecoded("/post/search", "postSearchController");

        Tomcat.addServlet(context, "postCreateController", (Servlet) applicationContext.getBean("postCreateController"));
        context.addServletMappingDecoded("/post/create", "postCreateController");

        tomcat.start();
        tomcat.getServer().await();
    }
}
