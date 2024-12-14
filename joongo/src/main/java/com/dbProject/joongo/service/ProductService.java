package com.dbProject.joongo.service;

import com.dbProject.joongo.aws.s3.AmazonS3Manager;
import com.dbProject.joongo.domain.Product;
import com.dbProject.joongo.domain.Uuid;
import com.dbProject.joongo.dto.product.ProductRequest;
import com.dbProject.joongo.dto.product.ProductRequest.ProductInfo;
import com.dbProject.joongo.dto.product.ProductResponse;
import com.dbProject.joongo.mapper.ProductMapper;
import com.dbProject.joongo.mapper.UuidMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMapper productMapper;
    private final AmazonS3Manager s3Manager;
    // image 필드가 pictureUrl 저장함
    // s3에 uuid 를 식별자로 단건 업로드 중
    public void create(ProductRequest.uploadInfo productInfo) {
        try {
            String uuid = UUID.randomUUID().toString();
            String pictureUrl = s3Manager.uploadFile(uuid,
                    productInfo.getProductPicture());

            Product product = productInfo.toEntity();
            product.setImage(pictureUrl);

            productMapper.insertProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[예상하지 못한 에러]: {}", e.getMessage());
            throw new RuntimeException("예상하지 못한 에러");
        }
    }

    public List<ProductResponse.ProductInfo> findAll() {
        try {
            List<Product> products = productMapper.findAll();
            return products.stream()
                    .filter(product -> product.getCount() > 0)
                    .map(ProductResponse.ProductInfo::fromEntity) // Entity → DTO 변환
                    .toList(); // 리스트로 변환
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[예상하지 못한 에러]: {}", e.getMessage());
            throw new RuntimeException("예상하지 못한 에러");
        }
    }

    public List<ProductResponse.ProductInfo> findAllByCategory(Integer categoryId) {
        try {
            List<Product> products = productMapper.findAllByCategoryId(categoryId);
            return products.stream()
                    .filter(product -> product.getCount() > 0)
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
