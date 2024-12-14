package com.dbProject.joongo.controller;

import com.dbProject.joongo.domain.Order;
import com.dbProject.joongo.dto.order.OrderRank;
import com.dbProject.joongo.dto.order.OrderRequest;
import com.dbProject.joongo.dto.order.OrderResponse;
import com.dbProject.joongo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<OrderResponse.createOrderDTO> createOrder(@RequestBody OrderRequest.createOrderDTO request) {
        OrderResponse.createOrderDTO result = orderService.createOrder(request);

        return ResponseEntity.ok(result);
    }

    // 특정 사용자의 거래 내역 가져오기
    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable int userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // 거래량 순위 가져오기
    @GetMapping("/rank")
    public ResponseEntity<List<OrderRank>> getUserRankByTransactionCount() {
        try {
            List<OrderRank> ranks = orderService.getUserRankByTransactionCount();
            return ResponseEntity.ok(ranks);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
