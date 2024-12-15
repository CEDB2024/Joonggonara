package com.dbProject.joongo.mapper;

import com.dbProject.joongo.domain.Order;
import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.dto.order.OrderRequest;
import com.dbProject.joongo.dto.order.OrderResponse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    void createOrder(OrderRequest.createOrderDTO dto);

    List<OrderResponse.findOrderDTO> selectOrderBySellerId(int sellerId);

    List<OrderResponse.findOrderDTO> selectOrderByBuyerId(int buyerId);
}
