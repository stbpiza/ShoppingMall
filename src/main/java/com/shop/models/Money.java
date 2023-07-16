package com.shop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Money {
    @Column(name = "amount")
    private Long amount;

    private Money() {
    }

    public Money(Long amount) {
        this.amount = amount;
    }

    public Long asLong() {
        return amount;
    }
    @Override
    public String toString() {
        return amount.toString();
    }
}
