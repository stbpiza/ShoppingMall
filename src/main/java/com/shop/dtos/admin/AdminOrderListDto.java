package com.shop.dtos.admin;

import java.util.List;

public record AdminOrderListDto(
        List<AdminOrderSummaryDto> orders
) {
}
