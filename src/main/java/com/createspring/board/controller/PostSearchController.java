package com.createspring.board.controller;

import com.createspring.board.entity.Post;
import com.createspring.board.repository.BoardRepository;
import com.createspring.board.service.BoardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PostSearchController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BoardRepository boardRepository = new BoardRepository();
        BoardService boardService = new BoardService(boardRepository);
        Post post = boardService.getPost(1L);
        System.out.println(post.toString());
    }
}
