package com.createspring.board.entity;

/**
 * DTO는 엔티티는 아니지만 검증용 기능이라 편의상 같은 패키지에 넣어 둠
 */
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String author;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public PostDTO(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
