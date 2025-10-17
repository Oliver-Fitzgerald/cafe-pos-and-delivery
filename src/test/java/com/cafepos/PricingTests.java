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
import com.cafepos.checkout.PricingService.PricingResult;
import com.cafepos.discount.*;

public class PricingTests {

    /**
     * pricingService_validateSubtotal
     * validates that the correct values are provided by PricingResult
     */
    @Test
    void pricingService_validateSubtotal() {

        PricingService pricing = new PricingService(new LoyaltyPercentDiscount(5), new FixedRateTaxPolicy(10));
        PricingResult pricingResult = pricing.price(Money.of(10));

        // Expected subtotal
        assertEquals(Money.of(10), pricing.subtotal());
        // Expected discount
        assertEquals(Money.of(0.5), pricing.discount());
        // Expected tax
        assertEquals(Money.of(0.95), pricing.tax());
        // Expected taxPercent
        assertEquals(10, pricing.taxPercent());
        // Expected total
        assertEquals(Money.of(10.45), pricing.total());
    }
}
