package com.shop.dtos;

import com.shop.models.Image;

public record ImageDto(
        String url
) {
    public static ImageDto of(Image image) {
        return new ImageDto(image.url());
    }
}