package com.shop.controllers;

import com.shop.security.UserRequired;
import com.shop.application.GetCartService;
import com.shop.dtos.CartDto;
import com.shop.models.UserId;
import com.shop.security.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@UserRequired
@RestController
@RequestMapping("/cart")
public class CartController {
    private final GetCartService getCartService;

    public CartController(GetCartService getCartService) {
        this.getCartService = getCartService;
    }

    @GetMapping
    public CartDto detail(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        UserId userId = new UserId(authUser.id());

        return getCartService.getCartDto(userId);
    }
}
