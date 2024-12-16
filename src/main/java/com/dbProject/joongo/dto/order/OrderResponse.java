package com.dbProject.joongo.dto.order;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

public class OrderResponse {

    @Data
    @Builder
    public static class createOrderDTO {
        private LocalDateTime createTime;
    }

    @Data
    @Builder
    public static class findOrderDTO {
        private Integer orderId;        // 주문 고유 번호
        private Integer productId;      // 상품 고유 번호 (FK)
        private Integer buyerId;        // 구매자 고유 번호
        private Integer sellerId;       // 판매자 고유 번호
        private Integer count;
        private LocalDateTime completedAt; // 거래 완료 시간
        private String title;
    }
}
