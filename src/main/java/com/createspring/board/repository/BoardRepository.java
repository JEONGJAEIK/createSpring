package com.createspring.board.repository;


import com.createspring.board.entity.Post;
import com.createspring.spring.annotation.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BoardRepository {
    Map<Long, Post> postMemory = new HashMap<>();

    public void save(Post p) {
        postMemory.put(p.getId(), p);
        System.out.println("인스턴스" + this);
    }

    public Post findById(Long id) {
        System.out.println("인스턴스" + this);
        return postMemory.get(id);
    }
}
