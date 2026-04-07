package com.createspring.board.service;


import com.createspring.board.controller.PostDTO;
import com.createspring.board.entity.Post;
import com.createspring.board.repository.BoardRepository;
import com.createspring.board.springevent.PostCreateEvent;
import com.createspring.board.springevent.PostSearchEvent;
import com.createspring.spring.annotation.Service;
import com.createspring.spring.event.ApplicationEventPublisher;

@Service
public class PostService {
    // TODO 현재는 옵저버 패턴만을 적용하여 발행자의 중복이 있다.
    //  스프링 이벤트리스너를 직접구현하고 발행자 중복을 없애보자
    private final BoardRepository boardRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PostService(BoardRepository boardRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.boardRepository = boardRepository;
        this.eventPublisher = applicationEventPublisher;
    }

    /**
     * 게시글작성
     */
    public void createPost(PostDTO dto) {
        Post post = new Post(dto.getId(), dto.getTitle(), dto.getContent(), dto.getAuthor());
        boardRepository.save(post);
        eventPublisher.publishEvent(new PostCreateEvent());
    }

    /**
     * 글 조회
     */
    public Post getPost(Long id) {
        Post post = boardRepository.findById(id);
        eventPublisher.publishEvent(new PostSearchEvent());
        return post;
    }
}
