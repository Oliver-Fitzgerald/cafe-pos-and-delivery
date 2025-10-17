package com.cafepos.demo;

import com.cafepos.smells.OrderManagerGod;
import com.cafepos.checkout.CheckoutService;
import com.cafepos.discount.LoyaltyPercentDiscount;
import com.cafepos.tax.FixedRateTaxPolicy;
import com.cafepos.checkout.ReceiptPrinter;
import com.cafepos.checkout.PricingService;
import com.cafepos.checkout.PricingService.PricingResult;

public final class Week6Demo {
    public static void main(String[] args) {

        // Old behavior
        String oldReceipt = OrderManagerGod.process("LAT+L", 2, "CARD", "LOYAL5", false);

        // New behavior
        PricingService pricing = new PricingService(new LoyaltyPercentDiscount(5), new FixedRateTaxPolicy(10));
        ReceiptPrinter printer = new ReceiptPrinter();
        CheckoutService checkout = new CheckoutService(new ProductFactory(), pricing, printer, 10);
        String newReceipt = checkout.checkout("LAT+L", 2);

        System.out.println("Old Receipt:\n" + oldReceipt);
        System.out.println("\nNew Receipt:\n" + newReceipt);
        System.out.println("\nMatch: " + oldReceipt.equals(newReceipt));
    }
}
