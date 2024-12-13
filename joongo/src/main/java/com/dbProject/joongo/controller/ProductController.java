package com.dbProject.joongo.controller;

import com.dbProject.joongo.dto.product.ProductRequest;
import com.dbProject.joongo.dto.product.ProductRequest.ProductInfo;
import com.dbProject.joongo.dto.product.ProductResponse;
import com.dbProject.joongo.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProduct(
            @ModelAttribute ProductRequest.uploadInfo request) {
        productService.create(request);

        return ResponseEntity.ok("Product created");
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponse.ProductInfo>> getAllProducts() {
        List<ProductResponse.ProductInfo> productInfos = productService.findAll();

        return ResponseEntity.ok(productInfos);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse.ProductInfo> getProductById(@PathVariable("productId") Integer productId) {
        ProductResponse.ProductInfo productInfo = productService.findProductById(productId);

        return ResponseEntity.ok(productInfo);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductResponse.ProductInfo>> getAllProductsByCategoryId(
            @RequestParam("categoryId") Integer categoryId) {
        List<ProductResponse.ProductInfo> productInfos = productService.findAllByCategory(categoryId);

        return ResponseEntity.ok(productInfos);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse.ProductInfo> updateProduct(@PathVariable("productId") Integer productId,
                                                                     @RequestBody ProductRequest.ProductInfo request) {
        ProductResponse.ProductInfo productInfo = productService.update(productId, request);

        return ResponseEntity.ok(productInfo);
    }
}
