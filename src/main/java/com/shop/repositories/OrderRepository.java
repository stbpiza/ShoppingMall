package com.shop.repositories;

import com.shop.models.Order;
import com.shop.models.OrderId;
import com.shop.models.UserId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, OrderId> {
    List<Order> findAllByUserIdOrderByIdDesc(UserId userId);

    Optional<Order> findByIdAndUserId(OrderId orderId, UserId userId);

    List<Order> findAllByOrderByIdDesc();
}
