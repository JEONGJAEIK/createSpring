package com.createspring.board;

import com.createspring.board.entity.PostDTO;
import com.createspring.spring.bean.BeanDefinition;
import com.createspring.spring.bean.context.BeanFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Test
    void 이벤트리스너_호출되고_트랜잭션이벤트리스너_호출() throws Exception {
        BeanFactory beanFactory = new BeanFactory();

        PostUtil mockPostUtil = mock(PostUtil.class);
        beanFactory.setBeanMap(mockPostUtil, new BeanDefinition(PostUtil.class));

        beanFactory.initialize("com.createspring");

        PostService service = (PostService) beanFactory.getBean("postService");

        service.createPost(new PostDTO(1L, "제목", "내용", "홍길동"));

        verify(mockPostUtil).say();
        verify(mockPostUtil).say3();
    }
}
