package com.shop.models;

import jakarta.persistence.Column;

public class ImageId {

    @Column(name = "id")
    private String value;

    private ImageId() {}

    public ImageId(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageId)) return false;
        ImageId imageId = (ImageId) o;
        return value.equals(imageId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
