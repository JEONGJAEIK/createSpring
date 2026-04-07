package com.createspring.board;

import com.createspring.board.entity.PostDTO;
import com.createspring.spring.bean.BeanDefinition;
import com.createspring.spring.bean.BeanFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Test
    void 이벤트리스너_호출되고_트랜잭션이벤트리스너_미호출() throws Exception {
        BeanFactory beanFactory = new BeanFactory();

        PostUtil mockPostUtil = mock(PostUtil.class);
        beanFactory.setBeanMap(mockPostUtil, new BeanDefinition(PostUtil.class));

        BoardRepository mockBoardRepository = mock(BoardRepository.class);
        beanFactory.setBeanMap(mockBoardRepository, new BeanDefinition(BoardRepository.class));

        beanFactory.initialize("com.createspring");

        PostService service = (PostService) beanFactory.getBean("postService");

        assertThrows(RuntimeException.class, () ->
                service.createPostThenFail(new PostDTO(2L, "제목", "내용", "홍길동")));

        verify(mockPostUtil).say();
        verify(mockPostUtil, never()).say3();
    }
}
