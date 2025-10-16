package com.cafepos.smells;

// cafepos
import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.domain.Product;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
import com.cafepos.discount.DiscountPolicy;
import com.cafepos.tax.TaxPolicy;
import com.cafepos.checkout.PricingService;
import com.cafepos.checkout.ReceiptPrinter;
import com.cafepos.payment.PaymentStrategy;

public class OrderManagerGod {

    //Global/Static State: `LAST_DISCOUNT_CODE`, `TAX_PERCENT` are global — risky and hard to test.
    //Primitive Obsession: `discountCode` strings; `TAX_PERCENT` as primitive; magic numbers for rates.
    public static int TAX_PERCENT = 10;
    public static Money LAST_DISCOUNT = null;

    //God Class & Long Method: One method performs creation, pricing, discounting, tax, payment I/O, and printing.
    public static String process(String recipe, int qty, PaymentStrategy paymentType, PricingService pricingService, boolean printReceipt) throws IllegalArgumentException {

        if (paymentType == null)
            throw new IllegalArgumentException("paymentType cannot be null");

        // Create product
        ProductFactory factory = new ProductFactory();
        Product product = factory.create(recipe);
        LineItem item = new LineItem(product, qty);

        // Create order
        Order order = new Order(OrderIds.next());
        order.addItem(item);

        // Payment
        PricingService.PricingResult pricingResult = pricingService.price(item.lineTotal());
        LAST_DISCOUNT = pricingResult.discount();
        order.pay(paymentType);

        // Receipt
        String receipt = new ReceiptPrinter().format(recipe, qty, pricingResult);

        if (printReceipt)
            System.out.println(receipt);

        return receipt;
    }
}
