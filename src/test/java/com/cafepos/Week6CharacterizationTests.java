package com.cafepos;

// cafepos
import com.cafepos.smells.OrderManagerGod;
import com.cafepos.common.Money;
import com.cafepos.discount.NoDiscount;
import com.cafepos.discount.LoyaltyPercentDiscount;
import com.cafepos.discount.FixedCouponDiscount;
// juinit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Week6CharacterizationTests {

    @Test void no_discount_cash_payment() {
        String receipt = OrderManagerGod.process("ESP+SHOT+OAT", 1, "CASH", new NoDiscount(), false);
        assertTrue(receipt.startsWith("Order (ESP+SHOT+OAT) x1"));
        assertTrue(receipt.contains("Subtotal: 3.80"));
        assertTrue(receipt.contains("Tax (10%): 0.38"));
        assertTrue(receipt.contains("Total: 4.18"));
    }

    @Test void loyalty_discount_card_payment() {

        String receipt = OrderManagerGod.process("LAT+L", 2, "CARD", new LoyaltyPercentDiscount(5), false);
        // Latte (Large) base = 3.20 + 0.70 = 3.90, qty 2 => 7.80
        // 5% discount => 0.39, discounted=7.41; tax 10% => 0.74; total=8.15
        assertTrue(receipt.contains("Subtotal: 7.80"));
        assertTrue(receipt.contains("Discount: -0.39"));
        assertTrue(receipt.contains("Tax (10%): 0.74"));
        assertTrue(receipt.contains("Total: 8.15"));
    }

    @Test void coupon_fixed_amount_and_qty_clamp() {
            String receipt = OrderManagerGod.process("ESP+SHOT", 0, "WALLET", new FixedCouponDiscount(Money.of(1)), false);
            // qty=0 clamped to 1; Espresso+SHOT = 2.50 + 0.80 = 3.30; coupon1 => -1 => 2.30; tax=0.23; total=2.53
            assertTrue(receipt.contains("Order (ESP+SHOT) x1"));
            assertTrue(receipt.contains("Subtotal: 3.30"));
            assertTrue(receipt.contains("Discount: -1.00"));
            assertTrue(receipt.contains("Tax (10%): 0.23"));
            assertTrue(receipt.contains("Total: 2.53"));
    }
}
