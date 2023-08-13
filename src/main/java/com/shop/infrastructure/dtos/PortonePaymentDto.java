package com.shop.infrastructure.dtos;

public record PortonePaymentDto(
        Response response
) {
    public record Response(
            Long amount
    ) {
    }
}
