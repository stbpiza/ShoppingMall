package com.shop.application;

import com.shop.models.CategoryId;
import com.shop.models.Image;
import com.shop.models.ImageId;
import com.shop.models.Money;
import com.shop.models.Product;
import com.shop.models.ProductOption;
import com.shop.models.ProductOptionId;
import com.shop.models.ProductOptionItem;
import com.shop.models.ProductOptionItemId;
import com.shop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateProductServiceTest {
    private ProductRepository productRepository;

    private CreateProductService createProductService;

    @BeforeEach
    void setUpMockObjects() {
        productRepository = mock(ProductRepository.class);

        createProductService = new CreateProductService(productRepository);
    }

    @Test
    void createProduct() {
        CategoryId categoryId = CategoryId.generate();
        List<Image> images = List.of(
                new Image(ImageId.generate(), "http://example.com/image.jpg")
        );
        String name = "New Product";
        Money price = new Money(100_000L);
        List<ProductOption> options = List.of(
                new ProductOption(
                        ProductOptionId.generate(),
                        "Option #1",
                        List.of(
                                new ProductOptionItem(
                                        ProductOptionItemId.generate(),
                                        "Item #1"
                                )
                        )
                )
        );
        String description = "Description";

        Product product = createProductService.createProduct(
                categoryId, images, name, price, options, description);

        assertThat(product.name()).isEqualTo(name);

        verify(productRepository).save(any());
    }
}