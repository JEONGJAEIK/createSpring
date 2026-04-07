package com.createspring.board.event;

import com.createspring.board.PostUtil;
import com.createspring.spring.annotation.Component;
import com.createspring.spring.annotation.EventListener;
import com.createspring.spring.annotation.TransactionEventListener;
import com.createspring.spring.transaction.TransactionPhase;

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

    @TransactionEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void hello3(PostCreateEvent postCreateEvent) {
        postUtil.say3();
    }
}
