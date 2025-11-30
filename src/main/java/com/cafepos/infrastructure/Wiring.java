package com.cafepos.infrastructure;

// Cafepos
import com.cafepos.application.CheckoutService;
import com.cafepos.checkout.PricingService;
import com.cafepos.discount.*;
import com.cafepos.tax.*;
import com.cafepos.domain.*;

/*
 * Wiring
 * Responsible for defining refrences to concrete implmentations of
 * interchangable system components
 */
public final class Wiring {

    public static record Components(OrderRepository repo, PricingService pricing, CheckoutService checkout) {}


    public static Components createDefault() {

        OrderRepository repo = new InMemoryOrderRepository();
        PricingService pricing = new PricingService(new
        LoyaltyPercentDiscount(5), new FixedRateTaxPolicy(10));
        CheckoutService checkout = new CheckoutService(repo, pricing);
        return new Components(repo, pricing, checkout);
    }
}
