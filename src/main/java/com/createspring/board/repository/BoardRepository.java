package com.createspring.board.repository;


import com.createspring.board.entity.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardRepository {
    static Map<Long, Post> postMemory = new HashMap<>();

    public void save(Post p) {
        postMemory.put(p.getId(), p);
    }

    public Post findById(Long id) {
        return postMemory.get(id);
    }
}
