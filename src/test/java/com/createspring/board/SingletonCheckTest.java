package com.createspring.board;

import com.createspring.spring.bean.context.AbstractApplicationContext;
import com.createspring.spring.bean.context.ApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SingletonCheckTest {

    @Test
    public void 만들어진_빈은_싱글톤이어야한다() throws Exception {
        ApplicationContext applicationContext = new AbstractApplicationContext();
        applicationContext.initialize("com.createspring");

        PostService service1 = (PostService) applicationContext.getBean("postService");
        PostService service2 = (PostService) applicationContext.getBean("postService");
        System.out.println(service1);
        System.out.println(service2);
        Assertions.assertSame(service1, service2);
    }
}
