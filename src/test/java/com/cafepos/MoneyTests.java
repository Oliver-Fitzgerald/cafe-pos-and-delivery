package com.cafepos;

import com.cafepos.common.Money;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoneyTests {

    @Test
    void money_addition() {
        Money a = Money.of(2.00);
        Money b = Money.of(3.00);
        Money result = a.add(b);
        assertEquals(Money.of(5.00), result);
    }

    @Test
    void money_multiplication() {
        Money a = Money.of(2.50);
        Money result = a.multiply(3);
        assertEquals(Money.of(7.50), result);
    }

    @Test
    void money_zero() {
        Money zero = Money.zero();
        assertEquals(Money.of(0.00), zero);
    }

    @Test
    void money_rounding() {
        Money a = Money.of(1.005);
        assertEquals("1.01", a.toString());

        Money b = Money.of(1.004);
        assertEquals("1.00", b.toString());
    }

    @Test
    void money_negative_throws() {
        assertThrows(IllegalArgumentException.class, () -> Money.of(-1.00));
    }

    @Test
    void money_multiply_by_decimal() {
        Money a = Money.of(10.00);
        Money result = a.multiply(0.1); // 10% tax
        assertEquals(Money.of(1.00), result);
    }
}
