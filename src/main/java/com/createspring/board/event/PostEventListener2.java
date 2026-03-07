package com.createspring.board.event;

import com.createspring.board.service.PostUtil;
import com.createspring.spring.annotation.Component;

@Component
public class PostEventListener2 implements BoardEventListener{
    private final PostUtil postUtil;

    public PostEventListener2(PostUtil postUtil) {
        this.postUtil = postUtil;
    }

    @Override
    public void onEvent() {
        postUtil.say2();
    }
}
