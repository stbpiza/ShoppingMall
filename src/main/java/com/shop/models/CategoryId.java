package com.shop.models;

import jakarta.persistence.Column;

import java.util.Objects;

public class CategoryId extends EntityId {
    private CategoryId() {
        super();
    }

    public CategoryId(String value) {
        super(value);
    }

    public static CategoryId generate() {
        return new CategoryId(newTsid());
    }
}
