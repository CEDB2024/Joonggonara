package com.dbProject.joongo.mapper;

import com.dbProject.joongo.domain.Product;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    void insertProduct(Product product);

    List<Product> findAll();

    Product findByCategoryId(int categoryId);

    void updateProduct(Product product);

    void deleteProductId(int productId);
}
