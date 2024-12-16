package com.dbProject.joongo.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Order {
    private Integer orderId;        // 주문 고유 번호
    private Integer productId;      // 상품 고유 번호 (FK)
    private Integer buyerId;        // 구매자 고유 번호
    private Integer sellerId;       // 판매자 고유 번호
    private Integer count;
    private LocalDateTime completedAt; // 거래 완료 시간
}
