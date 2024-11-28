package com.dbProject.joongo.domain;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class Badge {
    private Integer badgeId;  // 배지 고유 번호
    private Integer userId;   // 배지를 소유하는 유저 고유 번호
    private LocalDateTime createdAt; 
    private LocalDateTime updatedAt; 
}
