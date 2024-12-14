package com.dbProject.joongo.service;

import com.dbProject.joongo.domain.Order;
import com.dbProject.joongo.dto.order.OrderRequest;
import com.dbProject.joongo.dto.order.OrderResponse;
import com.dbProject.joongo.mapper.OrderMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderMapper orderMapper;

    public OrderResponse.createOrderDTO createOrder(OrderRequest.createOrderDTO request) {
        try {
            orderMapper.createOrder(request);

            return OrderResponse.createOrderDTO.builder()
                    .createTime(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error creating order", e);
        }
    }

    // 특정 사용자의 거래 내역 조회
    public List<Order> getOrdersByUserId(int userId) {
        return orderMapper.selectOrdersByUserId(userId);
    }

    // 거래량 순위 조회
    public List<Map<String, Object>> getUserRankByTransactionCount() {
        return orderMapper.selectUserRankByTransactionCount();
    }
}
