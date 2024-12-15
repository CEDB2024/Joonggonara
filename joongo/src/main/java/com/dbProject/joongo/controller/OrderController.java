package com.dbProject.joongo.controller;

import com.dbProject.joongo.domain.Order;
import com.dbProject.joongo.dto.order.OrderRequest;
import com.dbProject.joongo.dto.order.OrderResponse;
import com.dbProject.joongo.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<OrderResponse.createOrderDTO> createOrder(@RequestBody OrderRequest.createOrderDTO request) {
        OrderResponse.createOrderDTO result = orderService.createOrder(request);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{sellerId}/seller")
    public ResponseEntity<List<OrderResponse.findOrderDTO>> findBySellerId(@PathVariable("sellerId") int sellerId) {

        return ResponseEntity.ok(orderService.findBySellerId(sellerId));
    }

    @GetMapping("/{buyerId}/buyer")
    public ResponseEntity<List<OrderResponse.findOrderDTO>> findByBuyerId(@PathVariable("buyerId") int buyerId) {

        return ResponseEntity.ok(orderService.findByBuyerId(buyerId));
    }
}
