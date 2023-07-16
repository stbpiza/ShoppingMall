package com.shop.repositories;

import com.shop.models.CategoryId;
import com.shop.models.Product;
import com.shop.models.ProductId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, ProductId> {
    List<Product> findAll();

    List<Product> findAllByCategoryId(CategoryId categoryId);
}
