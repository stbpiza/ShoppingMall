package com.shop.exceptions;

public class EmailAlreadyTaken extends RuntimeException {
    public EmailAlreadyTaken(String email) {
        super(email);
    }
}
