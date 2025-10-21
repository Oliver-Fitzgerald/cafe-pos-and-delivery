package com.cafepos.checkout;

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

/*
 * CheckoutService
 * In charge of orchestrating orders
 */
public final class CheckoutService {

    private final ProductFactory factory;
    private final PricingService pricing;
    private final ReceiptPrinter printer;
    private final int taxPercent;

    public CheckoutService(ProductFactory factory, PricingService pricing, ReceiptPrinter printer, int taxPercent) {
        this.factory = factory;
        this.pricing = pricing;
        this.printer = printer;
        this.taxPercent = taxPercent;
    }

    public String checkout(String recipe, int qty, PaymentStrategy paymentMethod, boolean print) {

        // - create product via `ProductFactory`, 
        Product product = factory.create(recipe);

        // - compute subtotal from `Priced.price()` or `basePrice()`,
        LineItem item = new LineItem(product, qty);

        // - delegate pricing to `PricingService`, 
        PricingService.PricingResult pricingResult = pricing.price(item.lineTotal());
        
        // - delegate payment to a `PaymentStrategy`
        // Adapt to your Week-3 signature; if your strategy expects an Order, pass the real one here.
        Order order = new Order(OrderIds.next());
        order.addItem(item);
        order.pay(paymentMethod);
        // - delegate formatting to `ReceiptPrinter`.
        String receipt = printer.format(recipe, qty, pricingResult);
        if (print)
            System.out.println(receipt);
        return receipt;
    }
}
