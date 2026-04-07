package com.createspring.board.service;


import com.createspring.board.controller.PostDTO;
import com.createspring.board.entity.Post;
import com.createspring.board.repository.BoardRepository;
import com.createspring.board.event.PostCreateEvent;
import com.createspring.board.event.PostSearchEvent;
import com.createspring.spring.annotation.Service;
import com.createspring.spring.event.ApplicationEventPublisher;

@Service
public class PostService {
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
