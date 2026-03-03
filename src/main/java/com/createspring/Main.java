package com.createspring;

import com.createspring.board.controller.PostCreateController;
import com.createspring.board.controller.PostSearchController;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context context = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(context, "postSearchController", new PostSearchController());
        context.addServletMappingDecoded("/post/search", "postSearchController");

        Tomcat.addServlet(context, "postCreateController", new PostCreateController());
        context.addServletMappingDecoded("/post/create", "postCreateController");

        tomcat.start();
        tomcat.getServer().await();
    }
}
