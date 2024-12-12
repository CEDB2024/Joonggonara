package com.dbProject.joongo.service;

import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.dto.product.ProductRequest.ProductInfo;
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

    public void create(ProductInfo productInfo) {
        Product product = productInfo.toEntity();

        try {
            productMapper.insertProduct(product);
        } catch (DataAccessException e) {
            log.error("[SQL 에러]: {}", e.getMessage());
            throw new DataAccessException("SQL 에러") {
            };
        } catch (Exception e) {
            log.error("[예상하지 못한 에러]: {}", e.getMessage());
            throw new RuntimeException("예상하지 못한 에러");
        }
    }

    public List<ProductInfo> findAll() {
        try{
            List<Product> products = productMapper.findAll();
            return products.stream()
                    .map(ProductInfo::fromEntity) // Entity → DTO 변환
                    .toList(); // 리스트로 변환
        }  catch (DataAccessException e) {
            log.error("[SQL 에러]: {}", e.getMessage());
            throw new DataAccessException("SQL 에러") {
            };
        } catch (Exception e) {
            log.error("[예상하지 못한 에러]: {}", e.getMessage());
            throw new RuntimeException("예상하지 못한 에러");
        }
    }

    public List<ProductInfo> findAllByCategory(Integer categoryId) {
        List<Product> products = productMapper.findAllByCategoryId(categoryId);

        return products.stream()
                .map(ProductInfo::fromEntity)
                .toList();
    }

    public ProductInfo update(Integer productId, ProductInfo request) {
        Product product = request.toEntity();
        product.setProductId(productId);
        productMapper.updateProduct(product);

        return ProductInfo.fromEntity(product);
    }
}
