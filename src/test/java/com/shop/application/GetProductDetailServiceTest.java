package com.shop.application;

import com.shop.Fixtures;
import com.shop.dtos.admin.AdminProductDetailDto;
import com.shop.models.Category;
import com.shop.models.CategoryId;
import com.shop.models.Product;
import com.shop.models.ProductId;
import com.shop.repositories.CategoryRepository;
import com.shop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetProductDetailServiceTest {
    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    private GetProductDetailService getProductDeailService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);

        getProductDeailService = new GetProductDetailService(
                productRepository, categoryRepository);
    }

    @Test
    void getAdminProductDetailDto() {
        Product product = Fixtures.product("맨투맨");
        ProductId productId = product.id();

        CategoryId categoryId = product.categoryId();
        Category category = new Category(categoryId, "카테고리", false);

        given(productRepository.findById(productId))
                .willReturn(Optional.of(product));

        given(categoryRepository.findById(categoryId))
                .willReturn(Optional.of(category));

        AdminProductDetailDto productDto =
                getProductDeailService.getAdminProductDetailDto(productId);

        assertThat(productDto.name()).isEqualTo("맨투맨");
    }
}