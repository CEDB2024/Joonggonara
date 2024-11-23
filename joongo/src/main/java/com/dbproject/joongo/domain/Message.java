package com.dbproject.joongo.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Message {
    private Integer messageId;      // 쪽지 고유 번호
    private Integer roomId;         // 쪽지 방 고유 번호 (FK)
    private Integer senderId;       // 쪽지 전송자 고유 번호
    private String content;         // 쪽지 내용
    private LocalDateTime createdAt; // 쪽지 전송 시간
}
