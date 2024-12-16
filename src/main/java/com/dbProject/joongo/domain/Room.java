package com.dbProject.joongo.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Room {
    private Integer roomId;         // 쪽지 방 고유 번호
    private Integer sendUser;       // 쪽지 전송자 고유 번호
    private Integer receivedUser;   // 쪽지 수신자 고유 번호
    private Integer productId;      // 상품 고유 번호
    private LocalDateTime createdAt; // 방 생성 시간
    private LocalDateTime updatedAt; // 정보 수정 시간
}
