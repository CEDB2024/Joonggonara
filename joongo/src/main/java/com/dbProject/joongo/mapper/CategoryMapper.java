package com.dbProject.joongo.mapper;

import com.dbProject.joongo.domain.Category;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    List<Category> findAll();
}
