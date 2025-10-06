package com.cafepos.decorator;

// cafepos
import com.cafepos.domain.Product;
import com.cafepos.common.Money;

public final class OatMilk extends ProductDecorator {

    private static final Money surcharge = Money.of(0.50);

    /**
     * OatMilk
     */
    public OatMilk(Product base) {
        super(base);
    }

    /**
     * name
     * @return the base name with extra shot appended
     */
    @Override
    public String name() {
        return base.name() + " + Oat Milk";
    }

    /**
     * price
     * @return the base price with the added surcharge for this product
     */
    public Money price() {
        return (base instanceof ProductDecorator p ? p.price() : base.basePrice() ).add(surcharge);
    }
}
