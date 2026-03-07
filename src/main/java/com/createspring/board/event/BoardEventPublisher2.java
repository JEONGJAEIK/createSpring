package com.createspring.board.event;

import com.createspring.board.service.PostUtil;
import com.createspring.spring.annotation.Component;

import java.util.List;

@Component
public class BoardEventPublisher2 {
    private final List<BoardEventListener> boardEventListeners;

    public BoardEventPublisher2() {
        this.boardEventListeners = List.of(new PostEventListener2(new PostUtil()));
    }

    public void publishEvent() {
        for (BoardEventListener boardEventListener : boardEventListeners) {
            boardEventListener.onEvent();
        }
    }
}
