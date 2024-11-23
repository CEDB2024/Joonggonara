package com.dbproject.joongo.domain;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class Post {
    private Integer postId;       // 게시글 고유 번호
    private Integer userId;       // 게시글 작성자 고유 번호
    private String title;         // 제목
    private String content;       // 내용
    private String postType;      // 게시글 종류 (자유/신고 등).
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
