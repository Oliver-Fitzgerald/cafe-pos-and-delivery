package com.cafepos.discount;

// cafpos
import com.cafepos.common.Money;

public interface DiscountPolicy {
    Money discountOf(Money subtotal);
}
