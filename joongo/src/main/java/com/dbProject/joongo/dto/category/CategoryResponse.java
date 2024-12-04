package com.dbProject.joongo.dto.category;

import com.dbProject.joongo.domain.Category;
import lombok.Builder;
import lombok.Getter;

public class CategoryResponse {

    @Getter
    @Builder
    public static class CategoryNames {
        String name;

        public static CategoryNames fromEntity(final Category category) {
            return CategoryNames.builder()
                    .name(category.getCategoryName())
                    .build();
        }
    }
}
