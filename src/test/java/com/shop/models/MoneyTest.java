package com.shop.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {

    @Test
    void plus() {
        Money money = new Money(1000L);
        Money result = money.plus(new Money(500L));
        assertThat(result).isEqualTo(new Money(1500L));
    }

    @Test
    void times() {
        Money money = new Money(1000L);
        Money result = money.times(3);
        assertThat(result).isEqualTo(new Money(3000L));
    }
}