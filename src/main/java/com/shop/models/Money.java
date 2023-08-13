package com.shop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Money {
    public static final Money ZERO = new Money(0L);
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

    public Money times(int multiplier) {
        return new Money(amount * multiplier);
    }

    public Money plus(Money money) {
        return new Money(amount + money.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
