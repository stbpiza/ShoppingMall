package com.shop.application;

import com.shop.dtos.OrderListDto;
import com.shop.dtos.OrderSummaryDto;
import com.shop.models.Order;
import com.shop.models.UserId;
import com.shop.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetOrderListService {
    private final OrderRepository orderRepository;

    public GetOrderListService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderListDto getOrderList(UserId userId) {
        List<Order> orders =
                orderRepository.findAllByUserIdOrderByIdDesc(userId);

        return new OrderListDto(
                orders.stream()
                        .map(OrderSummaryDto::of)
                        .toList()
        );
    }
}
