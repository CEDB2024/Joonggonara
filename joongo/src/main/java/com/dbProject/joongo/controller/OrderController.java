package com.dbProject.joongo.controller;

import com.dbProject.joongo.dto.order.OrderRequest;
import com.dbProject.joongo.dto.order.OrderResponse;
import com.dbProject.joongo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
}
