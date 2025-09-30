package com.cafepos;

import com.cafepos.payment.CardPayment;
import com.cafepos.payment.PaymentStrategy;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.SimpleProduct;
import com.cafepos.common.Money;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class PaymentTests {

    @Test
    void cardPayment_pay() {

        // Capture the original System.out in a byte stream
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        try {

            // Execute Assert
            PaymentStrategy cardPayment = new CardPayment("1234 5678 9101 1212");
            Order order = new Order(1);
            order.addItem(new LineItem(new SimpleProduct("CK-K","Chicken Korma",Money.of(8.5)),1));
            cardPayment.pay(order);
            assertEquals("[Card] Customer paid 9.35 EUR with card **** **** **** 1212" + System.lineSeparator(),outContent.toString());

        } finally {
            // Restore System.out
            System.setOut(originalOut);
        }
    }

}
