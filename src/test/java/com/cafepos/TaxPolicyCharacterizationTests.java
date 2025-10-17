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
import com.cafepos.payment.CashPayment;
import com.cafepos.checkout.PricingService;
import com.cafepos.tax.*;

public class TaxPolicyCharacterizationTests {

    /** 
     * fixedRateTaxPolicy_taxValidation
     * Valudates that the correct amount is taxed when FixedRateTaxPolicy is applied
     */
    @Test void fixedRateTaxPolicy() {
        TaxPolicy taxPolicy = new FixedRateTaxPolicy(10);
        assertEquals(Money.of(0.74), taxPolicy.taxOn(Money.of(7.41)));
    }
}
