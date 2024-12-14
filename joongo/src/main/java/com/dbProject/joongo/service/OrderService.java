package com.dbProject.joongo.service;

import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.dto.order.OrderRequest;
import com.dbProject.joongo.dto.order.OrderRequest.createOrderDTO;
import com.dbProject.joongo.dto.order.OrderResponse;
import com.dbProject.joongo.mapper.OrderMapper;
import com.dbProject.joongo.mapper.ProductMapper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    @Transactional
    public OrderResponse.createOrderDTO createOrder(OrderRequest.createOrderDTO request) {
        try {
            orderMapper.createOrder(request);
            Product updateDto = getUpdateInfo(request);
            productMapper.updateProduct(updateDto);


            return OrderResponse.createOrderDTO.builder()
                    .createTime(LocalDateTime.now())
                    .build();
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("Error creating order", e);
        }
    }

    private Product getUpdateInfo(createOrderDTO request) {
        Product purchasedProduct = productMapper.findById(request.getProductId());
        if (purchasedProduct.getCount() < request.getCount()) {
            throw new RuntimeException("[Error] Product count is less than the requested product count");
        }
//
//        if (purchasedProduct.getCount().equals(request.getCount())) {
//            return null;
//        }
        return Product.builder()
                .productId(request.getProductId())
                .count(purchasedProduct.getCount() - request.getCount())
                .build();
    }
}
