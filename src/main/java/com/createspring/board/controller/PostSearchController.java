package com.createspring.board.controller;

import com.createspring.board.entity.Post;
import com.createspring.board.service.PostService;
import com.createspring.spring.annotation.RestController;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PostSearchController extends HttpServlet {
    private final PostService postService;

    public PostSearchController(PostService postService) {
        this.postService = postService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Post post = postService.getPost(1L);
        System.out.println(post.toString());
    }
}
