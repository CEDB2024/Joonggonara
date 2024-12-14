package com.dbProject.joongo.service;

import com.dbProject.joongo.dto.order.OrderRequest;
import com.dbProject.joongo.dto.order.OrderResponse;
import com.dbProject.joongo.mapper.OrderMapper;
import com.dbProject.joongo.mapper.ProductMapper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    public OrderResponse.createOrderDTO createOrder(OrderRequest.createOrderDTO request) {
        try {
            orderMapper.createOrder(request);
            productMapper.deleteProductId(request.getProductId());

            return OrderResponse.createOrderDTO.builder()
                    .createTime(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error creating order", e);
        }
    }
}
