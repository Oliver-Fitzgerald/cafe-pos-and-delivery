package com.cafepos;

// cafepos
import com.cafepos.smells.OrderManagerGod;
import com.cafepos.common.Money;
import com.cafepos.discount.NoDiscount;
import com.cafepos.discount.LoyaltyPercentDiscount;
import com.cafepos.discount.FixedCouponDiscount;
import com.cafepos.tax.FixedRateTaxPolicy;
import com.cafepos.checkout.PricingService;
import com.cafepos.payment.CashPayment;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.WalletPayment;
import com.cafepos.checkout.CheckoutService;
import com.cafepos.checkout.ReceiptPrinter;
import com.cafepos.factory.ProductFactory;
// juinit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Week6CharacterizationTests {

    @Test
    void orderGod_no_discount_cash_payment() {
        String receipt = OrderManagerGod.process("ESP+SHOT+OAT", 1, "CASH", "NONE", false);
        assertTrue(receipt.startsWith("Order (ESP+SHOT+OAT) x1"));
        assertTrue(receipt.contains("Subtotal: 3.80"));
        assertTrue(receipt.contains("Tax (10%): 0.38"));
        assertTrue(receipt.contains("Total: 4.18"));
    }
    @Test
    void checkoutService_no_discount_cash_payment() {
        CheckoutService checkoutService = new CheckoutService(new ProductFactory(), new PricingService(new NoDiscount(), new FixedRateTaxPolicy(10)), new ReceiptPrinter(),10);
        String receipt = checkoutService.checkout("ESP+SHOT+OAT", 1, new CashPayment(), false);
        assertTrue(receipt.startsWith("Order (ESP+SHOT+OAT) x1"));
        assertTrue(receipt.contains("Subtotal: 3.80"));
        assertTrue(receipt.contains("Tax (10%): 0.38"));
        assertTrue(receipt.contains("Total: 4.18"));
    }

    @Test
    void orderGod_loyalty_discount_card_payment() {

        String receipt = OrderManagerGod.process("LAT+L", 2, "CARD", "LOYAL5", false);
        // Latte (Large) base = 3.20 + 0.70 = 3.90, qty 2 => 7.80
        // 5% discount => 0.39, discounted=7.41; tax 10% => 0.74; total=8.15
        assertTrue(receipt.contains("Subtotal: 7.80"));
        assertTrue(receipt.contains("Discount: - 0.39"));
        assertTrue(receipt.contains("Tax (10%): 0.74"));
        assertTrue(receipt.contains("Total: 8.15"));
    }

    @Test
    void checkoutService_loyalty_discount_card_payment() {

        CheckoutService checkoutService = new CheckoutService(new ProductFactory(), new PricingService(new LoyaltyPercentDiscount(5), new FixedRateTaxPolicy(10)), new ReceiptPrinter(),10);
        String receipt = checkoutService.checkout("LAT+L", 2, new CardPayment("1212 1212 1111 2222"), false);
        // Latte (Large) base = 3.20 + 0.70 = 3.90, qty 2 => 7.80
        // 5% discount => 0.39, discounted=7.41; tax 10% => 0.74; total=8.15
        assertTrue(receipt.contains("Subtotal: 7.80"));
        assertTrue(receipt.contains("Discount: - 0.39"));
        assertTrue(receipt.contains("Tax (10%): 0.74"));
        assertTrue(receipt.contains("Total: 8.15"));
    }


    @Test
    void orderGod_couponFixedAmountAndQtyClamp() {

        String receipt = OrderManagerGod.process("ESP+SHOT", 1, "WALLET", "COUPON1", true);
        // qty=0 clamped to 1; Espresso+SHOT = 2.50 + 0.80 = 3.30; coupon1 => -1 => 2.30; tax=0.23; total=2.53
        assertTrue(receipt.contains("Order (ESP+SHOT) x1"));
        assertTrue(receipt.contains("Subtotal: 3.30"));
        assertTrue(receipt.contains("Discount: - 1.00"));
        assertTrue(receipt.contains("Tax (10%): 0.23"));
        assertTrue(receipt.contains("Total: 2.53"));
    }
    @Test
    void checkoutService_couponFixedAmountAndQtyClamp() {

        CheckoutService checkoutService = new CheckoutService(new ProductFactory(), new PricingService(new FixedCouponDiscount(Money.of(1)), new FixedRateTaxPolicy(10)), new ReceiptPrinter(),10);
        String receipt = checkoutService.checkout("ESP+SHOT", 1, new WalletPayment("WALLET"), false);
        // qty=0 clamped to 1; Espresso+SHOT = 2.50 + 0.80 = 3.30; coupon1 => -1 => 2.30; tax=0.23; total=2.53
        assertTrue(receipt.contains("Order (ESP+SHOT) x1"));
        assertTrue(receipt.contains("Subtotal: 3.30"));
        assertTrue(receipt.contains("Discount: - 1.00"));
        assertTrue(receipt.contains("Tax (10%): 0.23"));
        assertTrue(receipt.contains("Total: 2.53"));
    }
}
