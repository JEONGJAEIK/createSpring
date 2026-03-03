package com.createspring.board.controller;

import com.createspring.board.entity.Post;
import com.createspring.board.service.BoardService;
import com.createspring.spring.annotation.RestController;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PostSearchController extends HttpServlet {
    private final BoardService boardService;

    public PostSearchController(BoardService boardService) {
        this.boardService = boardService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Post post = boardService.getPost(1L);
        System.out.println(post.toString());
    }
}
