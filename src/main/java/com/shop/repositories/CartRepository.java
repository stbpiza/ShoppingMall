package com.shop.repositories;

import com.shop.models.Cart;
import com.shop.models.CartId;
import com.shop.models.UserId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, CartId> {
    Optional<Cart> findByUserId(UserId userId);
}
