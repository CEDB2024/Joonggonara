package com.dbProject.joongo.dto.order;

import lombok.Data;

public class OrderRequest {
    @Data
    public static class createOrderDTO {
        Integer productId;
        Integer buyerId;
        Integer sellerId;
        Integer count;
    }
}
