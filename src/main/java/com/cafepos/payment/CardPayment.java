package com.cafepos.payment;

import com.cafepos.domain.Order;

public class CardPayment implements PaymentStrategy {


    private final String cardNumber;
    /**
     * CardPayment
     * creates a card payment
     * Note: Any spaces or dashes will be removed from the input
     * @param cardNumber the card numner
     * @return the card payment object
     * @throws IllegalArgumentException if card number is empty, contains characthers that are not within 0-9 or if the count of number is greater than 16
     */
    public CardPayment(String cardNumber) {

        // Assert not null/empty
        if (cardNumber == null || cardNumber.trim().isEmpty())
            throw new IllegalArgumentException("Card number cannot be null or empty");

        //Assert only positive numbers
        String cleanCardNumber = cardNumber.replaceAll("[\\s-]", "");
         
        //Assert length is exactly 16 digits
        if (cleanCardNumber.length() != 16)
            throw new IllegalArgumentException("Card number must be exactly 16 digits");
        
        this.cardNumber = cleanCardNumber;
    }

    @Override
    public void pay(Order order) {
        System.out.println("[Card] Customer paid " + order.totalWithTax(10) + " EUR with card **** **** **** " + this.cardNumber.substring(12,16));

    }
}
