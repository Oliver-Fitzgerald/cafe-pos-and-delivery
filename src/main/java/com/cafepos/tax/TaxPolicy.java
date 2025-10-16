package com.cafepos.tax;

// cafepos
import com.cafepos.common.Money;

public interface TaxPolicy {
    Money taxOn(Money amount);
}
