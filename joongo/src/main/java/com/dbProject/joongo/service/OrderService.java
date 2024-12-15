package com.dbProject.joongo.service;

import com.dbProject.joongo.domain.Order;
import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.domain.User;
import com.dbProject.joongo.dto.order.OrderRequest;
import com.dbProject.joongo.dto.order.OrderRequest.createOrderDTO;
import com.dbProject.joongo.dto.order.OrderResponse;
import com.dbProject.joongo.mapper.OrderMapper;
import com.dbProject.joongo.mapper.ProductMapper;
import com.dbProject.joongo.mapper.UserMapper;
import java.time.LocalDateTime;
import java.util.List;
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
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
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
            throw new IllegalArgumentException("[ORDER] 잔액이 부족합니다.");
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
            throw new IllegalArgumentException("[ORDER] 수량보다 더 많이 구매하실 수 없습니다.");
        }

        if (purchasedProduct.getUserId().equals(request.getBuyerId())) {
            throw new IllegalArgumentException("[ORDER] 자신이 등록한 상품은 구매할 수 없습니다.");
        }
        String status = "available";
//        if (purchasedProduct.getCount().equals(request.getCount())) {
//            return null;
//        }
        if (purchasedProduct.getCount() - request.getCount() == 0) {
            status = "sold_out";
        }
        return Product.builder()
                .productId(request.getProductId())
                .price(purchasedProduct.getPrice())
                .count(purchasedProduct.getCount() - request.getCount())
                .productStatus(status)
                .build();
    }

    public List<OrderResponse.findOrderDTO> findBySellerId(int sellerId) {
        return orderMapper.selectOrderBySellerId(sellerId);
    }

    public List<OrderResponse.findOrderDTO>  findByBuyerId(int buyerId) {
        try {
            return orderMapper.selectOrderByBuyerId(buyerId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching order by buyerId", e);
        }
    }
}
