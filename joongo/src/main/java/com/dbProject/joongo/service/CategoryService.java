package com.dbProject.joongo.service;

import com.dbProject.joongo.domain.Categories;
import com.dbProject.joongo.dto.category.CategoryResponse;
import com.dbProject.joongo.dto.category.CategoryResponse.CategoryNames;
import com.dbProject.joongo.mapper.CategoryMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;

    public List<CategoryResponse.CategoryNames> findAll() {
        List<Categories> test = categoryMapper.getAllCategory();

        try {
            return categoryMapper.getAllCategory()
                    .stream()
                    .map(CategoryNames::fromEntity)
                    .toList();
        } catch (DataAccessException e) {
            log.error("[SQL 에러]: {}", e.getMessage());
            throw new IllegalStateException();
        } catch (Exception e) {
            log.error("[예상하지 못한 에러]: {}", e.getMessage());
            throw new IllegalStateException();
        }
    }
}
