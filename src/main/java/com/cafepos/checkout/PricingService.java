package com.cafepos.checkout;

// cafepos
import com.cafepos.tax.TaxPolicy;
import com.cafepos.discount.DiscountPolicy;
import com.cafepos.common.Money;

public final class PricingService {

    private final DiscountPolicy discountPolicy;
    private final TaxPolicy taxPolicy;

    public PricingService(DiscountPolicy discountPolicy, TaxPolicy taxPolicy) {
        this.discountPolicy = discountPolicy;
        this.taxPolicy = taxPolicy;
    }

    /**
     * price
     * Calculates the final price for an item
     * @returns PricingResult
     */
    public PricingResult price(Money subtotal) {

        // Discount
        Money discount = discountPolicy.discountOf(subtotal);
        Money discounted = Money.of(subtotal.getAmount().subtract(discount.getAmount()).doubleValue());
        if (discounted.getAmount().signum() < 0)
            discounted = Money.zero();

        // Tax
        Money tax = taxPolicy.taxOn(discounted);
        Money total = discounted.add(tax);

        // Result
        return new PricingResult(subtotal, discount, tax, taxPolicy.percent(), total);
    }

    /**
     * PricingResult
     * An object containg all of the information relevant to the composition of a products final price
     * @param subtotal The gross price of the product
     * @param discount The amount discounted from the final price
     * @param tax The tax applied to the product
     * @param taxPercent the percentage of the discounted price that was taxed
     * @param total the final price of the product
     */
    public static record PricingResult(Money subtotal, Money discount, Money tax, int taxPercent, Money total) {}
}
