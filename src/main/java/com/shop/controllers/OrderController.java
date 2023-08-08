package com.shop.controllers;

import com.shop.application.CreateOrderService;
import com.shop.dtos.OrderRequestDto;
import com.shop.models.Address;
import com.shop.models.Payment;
import com.shop.models.PhoneNumber;
import com.shop.models.PostalCode;
import com.shop.models.Receiver;
import com.shop.models.UserId;
import com.shop.security.AuthUser;
import com.shop.security.UserRequired;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@UserRequired
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final CreateOrderService createOrderService;

    public OrderController(CreateOrderService createOrderService) {
        this.createOrderService = createOrderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(
            Authentication authentication,
            @Valid @RequestBody OrderRequestDto orderRequestDto
    ) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        UserId userId = new UserId(authUser.id());

        OrderRequestDto.ReceiverDto receiverDto = orderRequestDto.receiver();
        OrderRequestDto.PaymentDto paymentDto = orderRequestDto.payment();

        Receiver receiver = new Receiver(
                receiverDto.name(),
                new Address(
                        receiverDto.address1(),
                        receiverDto.address2(),
                        new PostalCode(receiverDto.postalCode())
                ),
                new PhoneNumber(receiverDto.phoneNumber())
        );

        Payment payment = new Payment(
                paymentDto.merchantId(),
                paymentDto.transactionId()
        );

        createOrderService.createOrder(userId, receiver, payment);

        return "Created";
    }
}
