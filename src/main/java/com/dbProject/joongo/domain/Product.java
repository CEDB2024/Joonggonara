package com.dbProject.joongo.domain;

import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Builder
public class Product {
    private Integer productId;       // 상품 고유 번호
    private Integer userId;          // 유저 고유 번호 (FK)
    private String title;            // 상품 제목
    private String content;          // 상품 설명
    private String image;            // 상품 이미지 경로
    private Integer price;           // 상품 가격
    private String productStatus;    // 상품 상태 (구매 가능 등)
    private String location;         // 판매자 위치 정보
    private Integer categoryId;      // 카테고리 ID (FK)
    private Integer count;           // 조회수
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(userId, product.userId)
                && Objects.equals(title, product.title) && Objects.equals(content, product.content)
                && Objects.equals(image, product.image) && Objects.equals(price, product.price)
                && Objects.equals(productStatus, product.productStatus) && Objects.equals(location,
                product.location) && Objects.equals(categoryId, product.categoryId) && Objects.equals(
                count, product.count) && Objects.equals(createdAt, product.createdAt) && Objects.equals(
                updatedAt, product.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, userId, title, content, image, price, productStatus, location, categoryId, count,
                createdAt, updatedAt);
    }
}
