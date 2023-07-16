package com.shop.controllers;

import com.shop.application.GetCategoryListService;
import com.shop.dtos.CategoryDto;
import com.shop.dtos.CategoryListDto;
import com.shop.models.Category;
import com.shop.models.CategoryId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final GetCategoryListService getCategoryListService;

    public CategoryController(GetCategoryListService getCategoryListService) {
        this.getCategoryListService = getCategoryListService;
    }

    @GetMapping
    public CategoryListDto list() {
        List<Category> categories = getCategoryListService.getCategories();

        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> mapToDto(category))
                .toList();

        return new CategoryListDto(categoryDtos);
    }

    private CategoryDto mapToDto(Category category) {
        return new CategoryDto(category.id().toString(), category.name());
    }
}
