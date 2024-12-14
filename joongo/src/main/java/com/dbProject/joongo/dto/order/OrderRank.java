package com.dbProject.joongo.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRank {

    @Data
    @AllArgsConstructor
    public static class UserRank {
        private int userId;
        private String userName;
        private int transactionCount; // 거래량
    }

 

}
