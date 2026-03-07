package com.createspring.board.event;

import com.createspring.board.service.PostUtil;
import com.createspring.spring.annotation.Component;

@Component
public class PostEventListener implements BoardEventListener {
    private final PostUtil postUtil;

    public PostEventListener(PostUtil postUtil) {
        this.postUtil = postUtil;
    }

    @Override
    public void onEvent() {
        postUtil.say();
    }
}
