package com.cafepos;

// java
import java.util.stream.Stream;
// junit
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// cafepos
import com.cafepos.common.Money;
import com.cafepos.smells.OrderManagerGod;
import com.cafepos.tax.FixedRateTaxPolicy;
import com.cafepos.payment.CashPayment;
import com.cafepos.checkout.PricingService;
import com.cafepos.discount.*;
import com.cafepos.checkout.CheckoutService;
import com.cafepos.checkout.ReceiptPrinter;
import com.cafepos.factory.ProductFactory;

public class DiscountPolicyCharacterizationTests {

    /**
     * noDiscount_receiptValidation
     * Ensures that if NoDiscount is passed to OrderManagerGod.process that
     * the Discount portion of the receipt is not present
     */
    @Test
    void noDiscount_receiptValidation() {
        
        CheckoutService checkoutService = new CheckoutService(new ProductFactory(), new PricingService(new NoDiscount(), new FixedRateTaxPolicy(10)), new ReceiptPrinter(),10);
        String receipt = checkoutService.checkout("ESP+SHOT+OAT", 2, new CashPayment(), false);
        assertTrue(!receipt.contains("Discount:"));
    }

    /**
     * discountPolicy_receiptValidation
     * Ensures that if a discount is passed to OrderManagerGod.process that
     * the Discount portion of the receipt is present
     */
    @ParameterizedTest
    @MethodSource("receiptTestData")
    void discountPolicy_receiptValidation(DiscountPolicy policy) {
        
        CheckoutService checkoutService = new CheckoutService(new ProductFactory(), new PricingService(policy, new FixedRateTaxPolicy(10)), new ReceiptPrinter(),10);
        String receipt = checkoutService.checkout("ESP+SHOT+OAT", 2, new CashPayment(), false);
        assertTrue(receipt.contains("Discount:"));
    }
    static Stream<Arguments> receiptTestData() {
        return Stream.of(
            Arguments.of(new LoyaltyPercentDiscount(10)),
            Arguments.of(new FixedCouponDiscount(Money.of(10)))
        );
    }

    /** 
     * loayltyPercentDiscount_discountValidation
     * Valudates that the correct amount is discounted for a LoyaltyPercentDiscount
     */
    @Test 
    void loayltyPercentDiscount_discountValidation() {

        DiscountPolicy discount = new LoyaltyPercentDiscount(5);
        assertEquals(Money.of(0.39), discount.discountOf(Money.of(7.80)));
    }

    /** 
     * fixedCouponDiscount_discountValidation
     * Valudates that the correct amount is discounted for a FixedCouponDiscount
     */
    @Test 
    void fixedCouponDiscount_discountValidation() {

        DiscountPolicy discount = new FixedCouponDiscount(Money.of(5));
        assertEquals(Money.of(5), discount.discountOf(Money.of(10)));
    }
}
