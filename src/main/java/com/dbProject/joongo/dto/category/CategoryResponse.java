package com.dbProject.joongo.dto.category;

import com.dbProject.joongo.domain.Categories;
import lombok.Builder;
import lombok.Getter;

public class CategoryResponse {

    @Getter
    @Builder
    public static class CategoryNames {
        String name;

        public static CategoryNames fromEntity(final Categories categories) {
            return CategoryNames.builder()
                    .name(categories.getCategoryName())
                    .build();
        }
    }
}
