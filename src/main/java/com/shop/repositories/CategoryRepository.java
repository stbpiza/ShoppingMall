package com.shop.repositories;

import com.shop.models.Category;
import com.shop.models.CategoryId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, CategoryId> {
    List<Category> findAll();

    List<Category> findAllByOrderByIdAsc();

    List<Category> findAllByHiddenIsFalseOrderByIdAsc();
}
