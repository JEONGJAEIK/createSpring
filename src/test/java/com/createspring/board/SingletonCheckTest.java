package com.createspring.board;

import com.createspring.spring.bean.BeanFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class SingletonCheckTest {

    @Test
    public void 만들어진_빈은_싱글톤이어야한다() throws IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.initialize("com.createspring");
        
        PostService service1 = (PostService) beanFactory.getBean("postService");
        PostService service2 = (PostService) beanFactory.getBean("postService");
        System.out.println(service1);
        System.out.println(service2);
        Assertions.assertSame(service1, service2);
    }
}
