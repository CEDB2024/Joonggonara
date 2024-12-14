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
}
