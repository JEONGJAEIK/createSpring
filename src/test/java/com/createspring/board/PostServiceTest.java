package com.createspring.board;

import com.createspring.board.entity.PostDTO;
import com.createspring.spring.bean.BeanFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {

    @Test
    void createPost() throws IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.initialize("com.createspring");

        PostService service = (PostService) beanFactory.getBean("postService");
        service.createPost(new PostDTO(1L, "제목", "내용", "홍길동"));

    }
}