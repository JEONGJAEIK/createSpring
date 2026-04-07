package com.createspring.board.event;

import com.createspring.board.service.PostUtil;
import com.createspring.spring.annotation.Component;
import com.createspring.spring.annotation.EventListener;

@Component
public class PostEventListener {
    private final PostUtil postUtil;

    public PostEventListener(PostUtil postUtil) {
        this.postUtil = postUtil;
    }

    @EventListener
    public void hello(PostCreateEvent postCreateEvent) {
        postUtil.say();
    }

    @EventListener
    public void hello2(PostSearchEvent postSearchEvent) {
        postUtil.say2();
    }
}
