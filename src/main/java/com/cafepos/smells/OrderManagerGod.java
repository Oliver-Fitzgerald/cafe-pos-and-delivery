package com.cafepos.smells;

// cafepos
import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.domain.Product;

public class OrderManagerGod {

    //Global/Static State: `LAST_DISCOUNT_CODE`, `TAX_PERCENT` are global — risky and hard to test.
    //Primitive Obsession: `discountCode` strings; `TAX_PERCENT` as primitive; magic numbers for rates.
    public static int TAX_PERCENT = 10;
    public static String LAST_DISCOUNT_CODE = null;

    //God Class & Long Method: One method performs creation, pricing, discounting, tax, payment I/O, and printing.
    public static String process(String recipe, int qty, String paymentType, String discountCode, boolean printReceipt) {

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


        //Feature Envy / Shotgun Surgery: Tax/discount rules embedded inline; any change requires editing this method.
        Money discount = Money.zero();
        if (discountCode != null) {
            //Primitive Obsession: `discountCode` strings; `TAX_PERCENT` as primitive; magic numbers for rates.
            if (discountCode.equalsIgnoreCase("LOYAL5")) {
                //Duplicated Logic: Money and BigDecimal manipulations scattered inline.
                //Feature Envy: Using data from money directly instead of through it's interfaces
                discount = Money.of(subtotal.getAmount().multiply(java.math.BigDecimal.valueOf(5)).divide(java.math.BigDecimal.valueOf(100)).doubleValue());

            //Primitive Obsession: `discountCode` strings; `TAX_PERCENT` as primitive; magic numbers for rates.
            } else if (discountCode.equalsIgnoreCase("COUPON1")) {
                discount = Money.of(1.00);

            //Primitive Obsession: `discountCode` strings; `TAX_PERCENT` as primitive; magic numbers for rates.
            } else if (discountCode.equalsIgnoreCase("NONE")) {
                discount = Money.zero();

            } else {
                discount = Money.zero();
            }

            LAST_DISCOUNT_CODE = discountCode;
        }

        //Duplicated Logic: Money and BigDecimal manipulations scattered inline.
        //Feature Envy: Using data from money directly instead of through it's interfaces
        Money discounted = Money.of(subtotal.getAmount().subtract(discount.getAmount()).doubleValue());
        if (discounted.getAmount().signum() < 0)
            discounted = Money.zero();

        //Duplicated Logic: Money and BigDecimal manipulations scattered inline.
        //Feature Envy: Using data from money directly instead of through it's interfaces
        var tax = Money.of(discounted.getAmount().multiply(java.math.BigDecimal.valueOf(TAX_PERCENT)).divide(java.math.BigDecimal.valueOf(100)).doubleValue());
        var total = discounted.add(tax);

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

        //Shotgun Surgery and Duplicated Logic: PaymentStrategy already handles this logic
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order (").append(recipe).append(") x").append(qty).append("\n");
        receipt.append("Subtotal: ").append(subtotal).append("\n");


        if (discount.getAmount().signum() > 0) {
            receipt.append("Discount: - ").append(discount).append("\n");
        }

        receipt.append("Tax (").append(TAX_PERCENT).append("%): ").append(tax).append("\n");
        receipt.append("Total: ").append(total);

        String out = receipt.toString();
        if (printReceipt) {
            System.out.println(out);
        }
        return out;
    }
}
