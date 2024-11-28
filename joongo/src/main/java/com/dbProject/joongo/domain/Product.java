package com.dbProject.joongo.domain;

import lombok.Data;
import java.time.LocalDateTime;


@Data
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
}
