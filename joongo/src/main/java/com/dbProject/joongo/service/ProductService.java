package com.dbProject.joongo.service;

import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.dto.product.ProductRequest.ProductInfo;
import com.dbProject.joongo.dto.product.ProductResponse;
import com.dbProject.joongo.mapper.ProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMapper productMapper;

    public void create(ProductInfo productInfo) {
        Product product = productInfo.toEntity();

        try {
            productMapper.insertProduct(product);
        } catch (Exception e) {
            log.error("[예상하지 못한 에러]: {}", e.getMessage());
            throw new RuntimeException("예상하지 못한 에러");
        }
    }

    public List<ProductResponse.ProductInfo> findAll() {
        try {
            List<Product> products = productMapper.findAll();
            return products.stream()
                    .map(ProductResponse.ProductInfo::fromEntity) // Entity → DTO 변환
                    .toList(); // 리스트로 변환
        } catch (Exception e) {
            log.error("[예상하지 못한 에러]: {}", e.getMessage());
            throw new RuntimeException("예상하지 못한 에러");
        }
    }

    public List<ProductResponse.ProductInfo> findAllByCategory(Integer categoryId) {
        try {
            List<Product> products = productMapper.findAllByCategoryId(categoryId);
            return products.stream()
                    .map(ProductResponse.ProductInfo::fromEntity)
                    .toList();
        } catch (Exception e) {
            log.error("[예상하지 못한 에러]: {}", e.getMessage());
            throw new RuntimeException("예상하지 못한 에러");
        }
    }

    public ProductResponse.ProductInfo update(Integer productId, ProductInfo request) {
        try {
            Product product = request.toEntity();
            product.setProductId(productId);
            productMapper.updateProduct(product);
            return ProductResponse.ProductInfo.fromEntity(product);
        } catch (Exception e) {
            log.error("[예상하지 못한 에러]: {}", e.getMessage());
            throw new RuntimeException("예상하지 못한 에러");
        }
    }

    public ProductResponse.ProductInfo findProductById(Integer id) {
        try {
            Product product = productMapper.findById(id);
            return ProductResponse.ProductInfo.fromEntity(product);
        } catch (Exception e) {
            log.error("[예상하지 못한 에러]: {}", e.getMessage());
            throw new RuntimeException("예상하지 못한 에러");
        }
    }
}
