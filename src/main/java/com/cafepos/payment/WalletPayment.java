package com.cafepos.payment;

import com.cafepos.domain.Order;

public class WalletPayment implements PaymentStrategy {


    private final String walletId;
    /**
     * CardPayment
     * creates a card payment
     * Note: Any spaces or dashes will be removed from the input
     * @param walletId the card numner
     * @return the card payment object
     * @throws IllegalArgumentException if card number is empty, contains characthers that are not within 0-9 or if the count of number is greater than 16
     */
    public WalletPayment(String walletId) {

        this.walletId = walletId;
    }

    @Override
    public void pay(Order order) {
        System.out.println("[Wallet] Customer paid " + order.totalWithTax(10) + " EUR via wallet " + this.walletId);

    }
}
