package com.shop.models;

import jakarta.persistence.Column;

import java.util.Objects;

public class ProductOptionId extends EntityId {
    private ProductOptionId() {
        super();
    }

    public ProductOptionId(String value) {
        super(value);
    }

    public static ProductOptionId generate() {
        return new ProductOptionId(newTsid());
    }
}
