package com.cafepos.smells;

// cafepos
import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.domain.Product;
import com.cafepos.discount.DiscountPolicy;
import com.cafepos.tax.TaxPolicy;
import com.cafepos.checkout.PricingService;
import com.cafepos.checkout.ReceiptPrinter;

public class OrderManagerGod {

    //Global/Static State: `LAST_DISCOUNT_CODE`, `TAX_PERCENT` are global — risky and hard to test.
    //Primitive Obsession: `discountCode` strings; `TAX_PERCENT` as primitive; magic numbers for rates.
    public static int TAX_PERCENT = 10;
    public static Money LAST_DISCOUNT = null;

    //God Class & Long Method: One method performs creation, pricing, discounting, tax, payment I/O, and printing.
    public static String process(String recipe, int qty, String paymentType, PricingService pricingService, boolean printReceipt) {

        ProductFactory factory = new ProductFactory();
        Product product = factory.create(recipe);

        //Duplicated Logic: Already existing Line item logic for this.
        Money unitPrice;
        try {
            var priced = product instanceof com.cafepos.decorator.ProductDecorator p ? p.price() : product.basePrice();
            unitPrice = priced;
        } catch (Exception e) {
            unitPrice = product.basePrice();
        }

        //Duplicated Logic: Already existing Line item logic for this.
        if (qty <= 0)
            qty = 1;
        Money subtotal = unitPrice.multiply(qty);

        PricingService.PricingResult pricingResult = pricingService.price(subtotal);
        Money total = pricingResult.total();
        LAST_DISCOUNT = pricingResult.discount();

        //Shotgun Surgery and Duplicated Logic: PaymentStrategy already handles this logic
        if (paymentType != null) {

            //Primitive Obsession: `discountCode` strings; `TAX_PERCENT` as primitive; magic numbers for rates.
            if (paymentType.equalsIgnoreCase("CASH")) {
                System.out.println("[Cash] Customer paid " + total + " EUR");

            //Primitive Obsession: `discountCode` strings; `TAX_PERCENT` as primitive; magic numbers for rates.
            } else if (paymentType.equalsIgnoreCase("CARD")) {
                System.out.println("[Card] Customer paid " + total + " EUR with card ****1234");

            //Primitive Obsession: `discountCode` strings; `TAX_PERCENT` as primitive; magic numbers for rates.
            } else if (paymentType.equalsIgnoreCase("WALLET")) {
                System.out.println("[Wallet] Customer paid " + total + " EUR via wallet user-wallet-789");

            } else {
                System.out.println("[UnknownPayment] " + total);

            }
        }

        // Receipt
        String receipt = new ReceiptPrinter().format(recipe, qty, pricingResult);

        if (printReceipt)
            System.out.println(receipt);

        return receipt;
    }
}
