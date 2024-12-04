package com.dbProject.joongo.controller;

import com.dbProject.joongo.dto.category.CategoryResponse;
import com.dbProject.joongo.dto.category.CategoryResponse.CategoryNames;
import com.dbProject.joongo.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<CategoryNames>> getCategoryName() {
        List<CategoryNames> result = categoryService.findAll();

        return ResponseEntity.ok(result);
    }

}
