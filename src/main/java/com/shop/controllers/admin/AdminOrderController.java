package com.shop.controllers.admin;

import com.shop.application.GetAdminOrderDetailService;
import com.shop.application.GetOrderListService;
import com.shop.application.UpdateOrderService;
import com.shop.dtos.admin.AdminOrderDetailDto;
import com.shop.dtos.admin.AdminOrderListDto;
import com.shop.dtos.admin.AdminUpdateOrderDto;
import com.shop.models.OrderId;
import com.shop.models.OrderStatus;
import com.shop.security.AdminRequired;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AdminRequired
@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {
    private final GetOrderListService getOrderListService;
    private final GetAdminOrderDetailService getAdminOrderDetailService;
    private final UpdateOrderService updateOrderService;

    public AdminOrderController(GetOrderListService getOrderListService,
                                GetAdminOrderDetailService getAdminOrderDetailService, UpdateOrderService updateOrderService) {
        this.getOrderListService = getOrderListService;
        this.getAdminOrderDetailService = getAdminOrderDetailService;
        this.updateOrderService = updateOrderService;
    }

    @GetMapping
    public AdminOrderListDto list() {
        return getOrderListService.getAdminOrderList();
    }

    @GetMapping("/{id}")
    public AdminOrderDetailDto detail(@PathVariable String id) {
        OrderId orderId = new OrderId(id);
        return getAdminOrderDetailService.getOrderDetail(orderId);
    }

    @PatchMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @RequestBody AdminUpdateOrderDto orderDto) {
        OrderId orderId = new OrderId(id);
        OrderStatus status = OrderStatus.of(orderDto.status());
        updateOrderService.updateOrderStatus(orderId, status);
        return "Updated";
    }
}
