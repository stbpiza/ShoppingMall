package com.shop.dtos;

import com.shop.models.Category;
import com.shop.models.Product;

import java.util.List;
import java.util.stream.IntStream;

public record ProductDetailDto(
        String id,
        CategoryDto category,
        List<ImageDto> images,
        String name,
        Long price,
        List<ProductOptionDto> options,
        String description
) {
    public static ProductDetailDto of(Product product, Category category) {
        return new ProductDetailDto(
                product.id().toString(),
                CategoryDto.of(category),
                product.images().stream()
                        .map(image -> ImageDto.of(image)).toList(),
                product.name(),
                product.price().asLong(),
                IntStream.range(0, product.optionSize())
                        .mapToObj(index -> ProductOptionDto.of(product.option(index)))
                        .toList(),
                product.description()
        );
    }
}
