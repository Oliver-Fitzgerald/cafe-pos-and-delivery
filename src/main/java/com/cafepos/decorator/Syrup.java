package com.cafepos.decorator;

// cafepos
import com.cafepos.domain.Product;
import com.cafepos.common.Money;

public final class Syrup extends ProductDecorator {

    private static final Money surcharge = Money.of(0.40);

    /**
     * ExtraShot
     */
    public Syrup(Product base) {
        super(base);
    }

    /**
     * name
     * @return the base name with extra shot appended
     */
    @Override
    public String name() {
        return base.name() + " + Syrup";
    }

    /**
     * price
     * @return the base price with the added surcharge for this product
     */
    public Money price() {
        return (base.basePrice()).add(surcharge);
    }
}
