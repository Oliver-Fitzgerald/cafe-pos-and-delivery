package com.cafepos;

// junit
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;
// java
import java.util.stream.Stream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
// cafepos
import com.cafepos.domain.Order;
import com.cafepos.domain.Product;
import com.cafepos.domain.SimpleProduct;
import com.cafepos.domain.LineItem;
import com.cafepos.common.Money;
import com.cafepos.payment.PaymentStrategy;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.CashPayment;
import com.cafepos.payment.WalletPayment;

public class PaymentStrategyTests {

    static Product p;
    static Order order;

    @BeforeAll
    static void setup(){

        // Initalize an order and its products
        p = new SimpleProduct("A", "A", Money.of(5));
        order = new Order(42);
        order.addItem(new LineItem(p, 1));
    }

    @Test
    void paymentStrategyCalled() {

        // Create a mock implmentation of PaymentStrategy
        final boolean[] called = {false};
        PaymentStrategy mock = order -> called[0] = true;

        // Validate that mock is invoked by Order
        order.pay(mock);
        assertTrue(called[0], "Payment strategy should be called");
    }

    @ParameterizedTest
    @MethodSource("paymentStrategyOutputTestData")
    void paymentStrategyOutput(PaymentStrategy paymentMethod, String expectedOutput) {

        // Save the original System.out
        PrintStream originalOut = System.out;
        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // Redirect System.out to our PrintStream
        System.setOut(ps);
        
        try {
            order.pay(paymentMethod);
        } finally {
            // Restore System.out
            System.out.flush();
            System.setOut(originalOut);
        }
        
        assertEquals(baos.toString(), expectedOutput);
    }
    static Stream<Arguments> paymentStrategyOutputTestData() {
        return Stream.of(
            Arguments.of(new CashPayment(), "[Cash] Customer paid " + order.totalWithTax(10) + " EUR\n"),
            Arguments.of(new CardPayment("1111 2222 3333 1234"), "[Card] Customer paid " + order.totalWithTax(10) + " EUR with card **** **** **** 1234\n"),
            Arguments.of(new WalletPayment("bobs-wallet"), "[Wallet] Customer paid " + order.totalWithTax(10) + " EUR via wallet bobs-wallet\n")
        
        );
    }
}
