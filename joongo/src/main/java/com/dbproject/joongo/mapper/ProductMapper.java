package com.dbproject.joongo.mapper;

import com.dbproject.joongo.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM PRODUCTS WHERE seller_id = #{userId}")
    List<Product> selectProductsByUserId(int userId);
}
