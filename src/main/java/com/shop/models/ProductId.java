package com.shop.models;

import jakarta.persistence.Column;

public class ProductId extends EntityId {
    private ProductId() {
        super();
    }

    public ProductId(String value) {
        super(value);
    }

    public static ProductId generate() {
        return new ProductId(newTsid());
    }
}
