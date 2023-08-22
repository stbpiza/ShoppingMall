package com.shop.application;

import com.shop.Fixtures;
import com.shop.dtos.admin.AdminUpdateProductDto;
import com.shop.models.Product;
import com.shop.models.ProductId;
import com.shop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateProductServiceTest {
    private ProductRepository productRepository;

    private UpdateProductService updateProductService;

    @BeforeEach
    void setUpMockObjects() {
        productRepository = mock(ProductRepository.class);

        updateProductService = new UpdateProductService(productRepository);
    }

    @Test
    void updateProduct() {
        Product product = Fixtures.product("맨투맨");
        ProductId productId = product.id();

        given(productRepository.findById(productId))
                .willReturn(Optional.of(product));

        AdminUpdateProductDto productDto = new AdminUpdateProductDto(
                "Category-ID",
                List.of(),
                "New Name",
                product.price().asLong(),
                List.of(),
                "New Description",
                false
        );

        updateProductService.updateProduct(productId, productDto);

        assertThat(product.name()).isEqualTo("New Name");
    }
}