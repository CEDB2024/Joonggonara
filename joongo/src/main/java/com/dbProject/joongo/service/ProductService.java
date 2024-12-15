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

    public Product updateProduct(Product newProduct) {
        try {
            // 기존 Product 조회
            Product existingProduct = productMapper.findById(newProduct.getProductId());

            // 기존 Product와 새로운 Product 비교
            Product updatedProduct = Product.builder()
                    .productId(existingProduct.getProductId()) // productId는 항상 유지
                    .title(isDifferent(newProduct.getTitle(), existingProduct.getTitle()) ? newProduct.getTitle() : null)
                    .content(isDifferent(newProduct.getContent(), existingProduct.getContent()) ? newProduct.getContent() : null)
                    .image(isDifferent(newProduct.getImage(), existingProduct.getImage()) ? newProduct.getImage() : null)
                    .price(isDifferent(newProduct.getPrice(), existingProduct.getPrice()) ? newProduct.getPrice() : null)
                    .productStatus(isDifferent(newProduct.getProductStatus(), existingProduct.getProductStatus()) ? newProduct.getProductStatus() : null)
                    .location(isDifferent(newProduct.getLocation(), existingProduct.getLocation()) ? newProduct.getLocation() : null)
                    .categoryId(isDifferent(newProduct.getCategoryId(), existingProduct.getCategoryId()) ? newProduct.getCategoryId() : null)
                    .count(isDifferent(newProduct.getCount(), existingProduct.getCount()) ? newProduct.getCount() : null)
                    .build();

            // DB 업데이트
            productMapper.updateProduct(updatedProduct);
            log.info("상품 수정 완료: {}", updatedProduct);

            return updatedProduct;

        } catch (Exception e) {
            log.error("상품 업데이트 중 예외 발생: {}", e.getMessage());
            throw new RuntimeException("상품 업데이트 실패");
        }
    }

    // 두 값이 다른지 확인하는 메서드
    private boolean isDifferent(Object newValue, Object oldValue) {
        if (newValue == null && oldValue == null) return false;
        if (newValue == null || oldValue == null) return true;
        return !newValue.equals(oldValue);
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

    public boolean deleteProductById(Integer productId) {
        try {
            productMapper.deleteProductById(productId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("예상하지 못한 에러");
        }
    }

    public List<Product> findAllByTitle(String productName) {
        try {
            return productMapper.searchByTitle(productName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("예상하지 못한 에러");
        }
    }
}
