package com.shop.application;

import com.shop.Fixtures;
import com.shop.dtos.OrderListDto;
import com.shop.models.Order;
import com.shop.models.OrderId;
import com.shop.models.OrderLineItem;
import com.shop.models.OrderLineItemId;
import com.shop.models.OrderStatus;
import com.shop.models.Payment;
import com.shop.models.Product;
import com.shop.models.Receiver;
import com.shop.models.UserId;
import com.shop.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.shop.TestUtils.createOrderOptions;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetOrderListServiceTest {
    private OrderRepository orderRepository;

    private GetOrderListService getOrderListService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);

        getOrderListService = new GetOrderListService(orderRepository);
    }

    @Test
    void getOrderList() {
        UserId userId = UserId.generate();

        Product product = Fixtures.product("맨투맨");

        List<OrderLineItem> lineItems = List.of(
                new OrderLineItem(
                        OrderLineItemId.generate(),
                        product,
                        createOrderOptions(product, new int[]{0, 0}),
                        1
                )
        );

        Receiver receiver = Fixtures.receiver("홍길동");
        Payment payment = Fixtures.payment();

        Order order = new Order(OrderId.generate(), UserId.generate(),
                lineItems, receiver, payment, OrderStatus.PAID);

        given(orderRepository.findAllByUserIdOrderByIdDesc(userId))
                .willReturn(List.of(order));

        OrderListDto orderListDto = getOrderListService.getOrderList(userId);

        assertThat(orderListDto.orders()).hasSize(1);
    }
}