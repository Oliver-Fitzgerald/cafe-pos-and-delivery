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

public class DiscountPolicyCharacterizationTests {

    /**
     * noDiscount_receiptValidation
     * Ensures that if NoDiscount is passed to OrderManagerGod.process that
     * the Discount portion of the receipt is not present
     */
    @Test
    void noDiscount_receiptValidation() {
        
        String receipt = OrderManagerGod.process("ESP+SHOT+L", 10, new CashPayment(), new PricingService(new NoDiscount(), new FixedRateTaxPolicy(10)), false);
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
        
        String receipt = OrderManagerGod.process("ESP+SHOT+L", 10, new CashPayment(), new PricingService(policy, new FixedRateTaxPolicy(10)), false);
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
