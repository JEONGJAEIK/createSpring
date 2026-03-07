package com.createspring.board.event;

import com.createspring.board.service.PostUtil;
import com.createspring.spring.annotation.Component;

import java.util.List;

@Component
public class BoardEventPublisher {
    private final List<BoardEventListener> boardEventListeners;

    public BoardEventPublisher() {
        this.boardEventListeners = List.of(new PostEventListener(new PostUtil()));
    }

    public void publishEvent() {
        for (BoardEventListener boardEventListener : boardEventListeners) {
            boardEventListener.onEvent();
        }
    }
}
