package com.dbProject.joongo.controller;

import com.dbProject.joongo.dto.product.ProductRequest;
import com.dbProject.joongo.dto.product.ProductRequest.ProductInfo;
import com.dbProject.joongo.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest.ProductInfo request) {
        productService.create(request);

        return ResponseEntity.ok("Product created");
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductInfo>>  getAllProducts() {
        List<ProductInfo> productInfos = productService.findAll();

        return ResponseEntity.ok(productInfos);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductInfo>>  getAllProductsByCategoryId(@RequestParam Integer categoryId) {
        List<ProductInfo> productInfos = productService.findAllByCategory(categoryId);

        return ResponseEntity.ok(productInfos);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductInfo> updateProduct(@PathVariable Integer productId, @RequestBody ProductRequest.ProductInfo request) {
        ProductInfo productInfo = productService.update(productId ,request);

        return ResponseEntity.ok(productInfo);
    }
}
