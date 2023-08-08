package com.shop.repositories;

import com.shop.models.Order;
import com.shop.models.OrderId;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, OrderId> {
}
