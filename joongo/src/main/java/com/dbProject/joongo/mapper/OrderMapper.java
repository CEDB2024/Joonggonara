package com.dbProject.joongo.mapper;

import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.dto.order.OrderRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    void createOrder(OrderRequest.createOrderDTO dto);
}
