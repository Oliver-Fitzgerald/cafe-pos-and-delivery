package com.cafepos.application;

// CafePOS
import com.cafepos.domain.*;
import com.cafepos.checkout.PricingService;
import com.cafepos.checkout.PricingService.PricingResult;

/*
 * CheckoutService
 * In charge of orchestrating orders
 */
public final class CheckoutService {

    private final OrderRepository orders;
    private final PricingService pricing;

    public CheckoutService(OrderRepository orders, PricingService pricing) {
        this.orders = orders;
        this.pricing = pricing;
    }

    /** 
     * checkout
     * @return a receipt string; does NOT print.
     */
    public String checkout(long orderId, int taxPercent) {

        Order order = orders.findById(orderId).orElseThrow();
        PricingResult result = pricing.price(order.subtotal());

        return new ReceiptFormatter().format(orderId, order.items(), result, taxPercent);
    }
}
