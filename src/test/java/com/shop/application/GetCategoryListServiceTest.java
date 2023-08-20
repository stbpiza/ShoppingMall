package com.shop.application;

import com.shop.models.Category;
import com.shop.models.CategoryId;
import com.shop.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetCategoryListServiceTest {
    private CategoryRepository categoryRepository;
    private GetCategoryListService getCategoryListService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        getCategoryListService = new GetCategoryListService(categoryRepository);
    }

    @Test
    void list() {
        Category category = new Category(new CategoryId("0BV000CAT0001"), "top");

        given(categoryRepository.findAllByHiddenIsFalseOrderByIdAsc())
                .willReturn(List.of(category));
        List<Category> categories = getCategoryListService.getCategories();

        assertThat(categories).hasSize(1);
        // 여기서 그냥 verify를 해도 된다. 너무 심각하게 뭔가를 테스트하려고 하지 말 것!
    }
}