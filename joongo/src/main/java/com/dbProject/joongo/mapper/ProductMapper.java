package com.dbProject.joongo.mapper;

import com.dbProject.joongo.domain.Product;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {
    void insertProduct(Product product);

    Product findById(int id);

    List<Product> findAll();

    List<Product> findAllByCategoryId(int categoryId);

    void updateProduct(Product product);

    void deleteProductId(int productId);
}
