package com.createspring.board.controller;
import com.createspring.board.dto.PostDTO;
import com.createspring.board.repository.BoardRepository;
import com.createspring.board.service.BoardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PostCreateController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BoardRepository boardRepository = new BoardRepository();
        BoardService boardService = new BoardService(boardRepository);
        PostDTO postDTO = new PostDTO(1L, "제목", "내용", "홍길동");
        boardService.createPost(postDTO);
    }
}
