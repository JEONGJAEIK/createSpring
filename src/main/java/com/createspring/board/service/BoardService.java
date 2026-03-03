package com.createspring.board.service;


import com.createspring.board.dto.PostDTO;
import com.createspring.board.entity.Post;
import com.createspring.board.repository.BoardRepository;
import com.createspring.spring.annotation.Service;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * 게시글작성
     */
    public void createPost(PostDTO dto) {
        Post post = new Post(dto.getId(), dto.getTitle(), dto.getContent(), dto.getAuthor());
        boardRepository.save(post);
        System.out.println("인스턴스" + this);
        System.out.println("게시글 작성 완료");
    }

    /**
     * 글 조회
     */
    public Post getPost(Long id) {
        System.out.println("게시글 조회 완료");
        System.out.println("인스턴스" + this);
        return boardRepository.findById(id);
    }
}
