package com.cafepos.discount;

// cafepos
import com.cafepos.common.Money;

public final class LoyaltyPercentDiscount implements DiscountPolicy {

    private final int percent;

    public LoyaltyPercentDiscount(int percent) throws IllegalArgumentException {
        if (percent < 0)
            throw new IllegalArgumentException();
        this.percent = percent;
    }

    @Override
    public Money discountOf(Money subtotal) {

        return subtotal.multiply(percent).divide(100);
    }
}
