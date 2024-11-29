package com.dbproject.joongo.service;

import com.dbproject.joongo.domain.Product;
import com.dbproject.joongo.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Product> getProductsByUserId(int userId) {
        return productMapper.selectProductsByUserId(userId);
    }
}
