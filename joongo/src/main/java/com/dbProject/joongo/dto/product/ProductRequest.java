package com.dbProject.joongo.dto.product;

import com.dbProject.joongo.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class ProductRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductInfo {
        private String title;
        private String content;
        private String image;
        private String productStatus; // Enum 으로 변경 예정
        private String location;
        private Integer count;
        private Integer price;
        private Integer categoryId;
        private Integer userId;

        public Product toEntity() {
            return Product.builder()
                    .title(this.title)
                    .content(this.content)
                    .image(this.image)
                    .productStatus(this.productStatus)
                    .location(this.location)
                    .count(this.count)
                    .price(this.price)
                    .categoryId(this.categoryId)
                    .userId(this.userId)
                    .build();
        }

        public static ProductInfo fromEntity(final Product product) {
            return ProductInfo.builder()
                    .title(product.getTitle())
                    .content(product.getContent())
                    .image(product.getImage())
                    .productStatus(product.getProductStatus())
                    .location(product.getLocation())
                    .count(product.getCount())
                    .price(product.getPrice())
                    .userId(product.getUserId())
                    .categoryId(product.getCategoryId())
                    .build();
        }
    }

    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class uploadInfo {
        private String title;
        private String content;
        private String location;
        private Integer count;
        private Integer price;
        private Integer categoryId;
        private Integer userId;
        private MultipartFile productPicture;

        public Product toEntity() {
            return Product.builder()
                    .title(this.title)
                    .content(this.content)
                    .productStatus("available")
                    .location(this.location)
                    .count(this.count)
                    .price(this.price)
                    .categoryId(this.categoryId)
                    .userId(this.userId)
                    .build();
        }
    }

    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateInfo {
        private Integer productId;
        private String title;
        private String content;
        private String location;
        private Integer count;
        private Integer price;
        private Integer categoryId;
        private Integer userId;
        private MultipartFile productPicture;

        public Product toEntity() {
            return Product.builder()
                    .productId(this.productId)
                    .title(this.title)
                    .content(this.content)
                    .productStatus("available")
                    .location(this.location)
                    .count(this.count)
                    .price(this.price)
                    .categoryId(this.categoryId)
                    .userId(this.userId)
                    .build();
        }
    }
}
