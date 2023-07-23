package com.shop.dtos;

public record LoginRequestDto(
        String email,
        String password
) {
}
