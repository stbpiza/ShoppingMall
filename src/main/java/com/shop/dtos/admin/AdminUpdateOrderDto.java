package com.shop.dtos.admin;

import jakarta.validation.constraints.NotBlank;

public record AdminUpdateOrderDto(
        @NotBlank
        String status
) {
}
