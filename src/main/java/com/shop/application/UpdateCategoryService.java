package com.shop.application;

import com.shop.models.Category;
import com.shop.models.CategoryId;
import com.shop.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateCategoryService {
    private final CategoryRepository categoryRepository;

    public UpdateCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void updateCategory(CategoryId id, String name, boolean hidden) {
        Category category = categoryRepository.findById(id)
                .orElseThrow();

        category.changeName(name);

        if (hidden) {
            category.hide();
            return;
        }

        category.show();
    }
}
