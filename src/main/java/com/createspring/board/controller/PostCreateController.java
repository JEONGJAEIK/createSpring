package com.createspring.board.controller;

import com.createspring.board.dto.PostDTO;
import com.createspring.board.service.PostService;
import com.createspring.spring.annotation.RestController;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PostCreateController extends HttpServlet {
    private final PostService postService;

    public PostCreateController(PostService postService) {
        this.postService = postService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        PostDTO postDTO = new PostDTO(1L, "제목", "내용", "홍길동");
        postService.createPost(postDTO);
    }
}
