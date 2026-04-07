package com.createspring.board;

import com.createspring.spring.annotation.Component;

@Component
public class PostUtil {

    public void say() {
        System.out.println("글을 썼다 냥냥");
    }

    public void say2() {
        System.out.println("글을 조회했다냥");
    }

    public void say3() {
        System.out.println("트랜잭션 커밋이 되고 호출됐다냐아앜!!");
    }
}
