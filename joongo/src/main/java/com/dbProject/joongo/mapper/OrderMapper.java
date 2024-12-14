package com.dbProject.joongo.mapper;

import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.domain.Order;
import com.dbProject.joongo.dto.order.OrderRequest;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    void createOrder(OrderRequest.createOrderDTO dto);

        // 특정 사용자 거래 내역 조회
    List<Order> selectOrdersByUserId(@Param("userId") int userId);

    // 사용자별 거래량 순위 조회
    List<Map<String, Object>> selectUserRankByTransactionCount();


}
