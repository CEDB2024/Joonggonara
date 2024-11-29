package com.dbProject.joongo.service;

import com.dbProject.joongo.domain.Category;
import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.dto.product.ProductRequestDto.ProductInfo;
import com.dbProject.joongo.mapper.ProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMapper productMapper;

    public int create(ProductInfo productInfo) {
        Product product = productInfo.toEntity();

        try {
            productMapper.insertProduct(product);
        } catch (DataAccessException e) {
            log.error("[SQL 에러]: {}", e.getMessage());
        } catch (Exception e) {
            log.error("[예상하지 못한 에러]: {}", e.getMessage());
        }


        return product.getProductId();
    }

    public List<ProductInfo> findAll() {
        return productMapper.findAll().stream()
                .map(ProductInfo::fromEntity) // Entity → DTO 변환
                .toList(); // 리스트로 변환
    }

    public ProductInfo findByCategory(Category category) {
        Integer categoryId = category.getCategoryId();

        if (categoryId == null) {
            throw new IllegalArgumentException();
        }
        Product product = productMapper.findByCategoryId(categoryId);

        return ProductInfo.fromEntity(product);
    }
}
