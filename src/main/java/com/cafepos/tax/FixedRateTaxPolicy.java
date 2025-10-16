package com.cafepos.tax;

// cafepos
import com.cafepos.common.Money;

public final class FixedRateTaxPolicy implements TaxPolicy {

    private final int percent;
    public FixedRateTaxPolicy(int percent) {
        if (percent < 0) 
            throw new IllegalArgumentException();
        this.percent = percent;
    }

    @Override
    public Money taxOn(Money amount) {
        return amount.multiply(percent).divide(100);
    }
}
