package com.shop.models;

import jakarta.persistence.Column;

import java.util.Objects;

public class ProductOptionId {

    @Column(name = "id")
    private String value;

    private ProductOptionId() {}

    public ProductOptionId(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOptionId that = (ProductOptionId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
