package com.shop.models;

import jakarta.persistence.Column;

public class ProductOptionItemId {
    @Column(name = "id")
    private String value;

    private ProductOptionItemId() {}

    public ProductOptionItemId(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductOptionItemId)) return false;
        ProductOptionItemId productOptionItemId = (ProductOptionItemId) o;
        return value.equals(productOptionItemId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
