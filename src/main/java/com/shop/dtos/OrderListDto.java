package com.shop.dtos;

import java.util.List;

public record OrderListDto(
        List<OrderSummaryDto> orders
) {
}
