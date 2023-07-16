package com.shop.dtos;

import com.shop.models.ProductOption;

import java.util.List;
import java.util.stream.IntStream;

public record ProductOptionDto(
        String id,
        String name,
        List<ProductOptionItemDto> items
) {
    public static ProductOptionDto of(ProductOption productOption) {
        return new ProductOptionDto(
                productOption.id().toString(),
                productOption.name(),
                IntStream.range(0, productOption.itemSize())
                        .mapToObj(index -> ProductOptionItemDto.of(productOption.item(index)))
                        .toList());
    }
}
