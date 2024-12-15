package com.dbProject.joongo.service;

import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.domain.User;
import com.dbProject.joongo.dto.order.OrderRequest;
import com.dbProject.joongo.dto.order.OrderRequest.createOrderDTO;
import com.dbProject.joongo.dto.order.OrderResponse;
import com.dbProject.joongo.mapper.OrderMapper;
import com.dbProject.joongo.mapper.ProductMapper;
import com.dbProject.joongo.mapper.UserMapper;
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
    private final UserMapper userMapper;

    @Transactional
    public OrderResponse.createOrderDTO createOrder(OrderRequest.createOrderDTO request) {
        try {
            orderMapper.createOrder(request);
            Product updateDto = getUpdateInfo(request);
            productMapper.updateProduct(updateDto);
            updateUsers(request, updateDto);

            return OrderResponse.createOrderDTO.builder()
                    .createTime(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating order", e);
        }
    }

    private void updateUsers(createOrderDTO request, Product updateDto) {
        User buyer = userMapper.selectUserById(request.getBuyerId());
        long payMoney = ((long) request.getCount() * updateDto.getPrice());
        if (buyer.getMoney() < payMoney) {
            // 원래는 에러마다 runtimeException 상속해서 커스텀 예외 만들어서 exceptionAdvice 에서 처리
            throw new RuntimeException("[Error] You do not have enough money!");
        }
        User seller = userMapper.selectUserById(request.getSellerId());
        User updatedBuyer = User.builder()
                .userId(request.getBuyerId())
                .money(buyer.getMoney() - payMoney)
                .build();
        User updateSeller = User.builder()
                .userId(request.getSellerId())
                .money(seller.getMoney() + payMoney)
                .build();
        userMapper.updateUser(updatedBuyer);
        userMapper.updateUser(updateSeller);
    }

    private Product getUpdateInfo(createOrderDTO request) {
        Product purchasedProduct = productMapper.findById(request.getProductId());
        if (purchasedProduct.getCount() < request.getCount()) {
            throw new RuntimeException("[Error] Product count is less than the requested product count");
        }
        productMapper.updateProduct(Product.builder()
                .productId(request.getProductId())
                .count(purchasedProduct.getCount() - request.getCount())
                .productStatus("sold_out")
                .build());
//        if (purchasedProduct.getCount().equals(request.getCount())) {
//            return null;
//        }
        return Product.builder()
                .productId(request.getProductId())
                .price(purchasedProduct.getPrice())
                .count(purchasedProduct.getCount() - request.getCount())
                .productStatus("sold_out")
                .build();
    }
}
