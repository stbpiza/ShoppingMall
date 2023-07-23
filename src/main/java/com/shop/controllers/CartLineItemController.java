package com.shop.controllers;

import com.shop.security.UserRequired;
import com.shop.application.AddProductToCartService;
import com.shop.dtos.AddProductToCartDto;
import com.shop.models.CartLineItemOption;
import com.shop.models.ProductId;
import com.shop.models.ProductOptionId;
import com.shop.models.ProductOptionItemId;
import com.shop.models.UserId;
import com.shop.security.AuthUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@UserRequired
@RestController
@RequestMapping("/cart/line-items")
public class CartLineItemController {
    private final AddProductToCartService addProductToCartService;

    public CartLineItemController(
            AddProductToCartService addProductToCartService) {
        this.addProductToCartService = addProductToCartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(
            Authentication authentication,
            @Valid @RequestBody AddProductToCartDto requestDto) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        UserId userId = new UserId(authUser.id());
        ProductId productId = new ProductId(requestDto.productId());
        Set<CartLineItemOption> options = requestDto.options().stream()
                .map(option -> new CartLineItemOption(
                        new ProductOptionId(option.id()),
                        new ProductOptionItemId(option.itemId())
                ))
                .collect(Collectors.toSet());
        int quantity = requestDto.quantity();

        addProductToCartService.addProductToCart(
                userId, productId, options, quantity);

        return "Created";
    }
}
