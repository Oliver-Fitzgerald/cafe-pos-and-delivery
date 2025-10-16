package com.cafepos.discount;

// cafepos
import com.cafepos.common.Money;

public final class FixedCouponDiscount implements DiscountPolicy {

    private final Money amount;

    public FixedCouponDiscount(Money amount) { this.amount = amount; }

    @Override
    public Money discountOf(Money subtotal) {
        // cap at subtotal
        if (amount.getAmount().compareTo(subtotal.getAmount()) > 0)
            return subtotal;
        return amount;
    }
}
