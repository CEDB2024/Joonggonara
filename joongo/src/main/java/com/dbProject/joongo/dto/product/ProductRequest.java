package com.dbProject.joongo.dto.product;

import com.dbProject.joongo.domain.Product;
import lombok.Builder;
import lombok.Getter;
public class ProductRequestDto {

    @Getter
    @Builder
    public static class ProductInfo {
        private String title;
        private String content;
        private String image;
        private String productStatus; // Enum으로 변경 예정
        private String location;
        private int count;
        private int price;

        public Product toEntity() {
            return Product.builder()
                    .title(this.title)
                    .content(this.content)
                    .image(this.image)
                    .productStatus(this.productStatus)
                    .location(this.location)
                    .count(this.count)
                    .price(this.price)
                    .build();
        }

        public static ProductInfo fromEntity(Product product) {
            return ProductInfo.builder()
                    .title(product.getTitle())
                    .content(product.getContent())
                    .image(product.getImage())
                    .productStatus(product.getProductStatus())
                    .location(product.getLocation())
                    .count(product.getCount())
                    .price(product.getPrice())
                    .build();
        }
    }
}
