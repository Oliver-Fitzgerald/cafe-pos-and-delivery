package com.cafepos.demo;

// java
import java.util.HashMap;
import java.util.Scanner;
// cafepos
import com.cafepos.payment.CashPayment;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.WalletPayment;
import com.cafepos.common.Money;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
import com.cafepos.domain.Product;
import com.cafepos.domain.SimpleProduct;

public class CafeMenu extends Menu {

    private static CafeMenu menuInstance;

    private CafeMenu(){
        super("Cafe Menu");
        addMenuItem("Create/Edit Order"); // 1
        addMenuItem("Pay Order"); // 2
        addMenuItem("Stock Store"); // 3
    }

    /**
     * getInstance
     * If there is an existing instance of CafeMenu it returns it
     * otherwise it will create an instance before returning it.
     */
    public static CafeMenu getInstance(){

        if (menuInstance == null) {
            menuInstance = new CafeMenu();
        }
        return menuInstance;
    }


    /**
     * execute
     * Performs/Creates the requested action/menu
     * @param choice - The chosen action to be performed
     */
    @Override
    public boolean execute(int choice) {

        switch (choice) {
            case 1: editOrder();
                    return false;
            case 2: payOrder();
                    return false;
            case 3: stockStore();
                    return false;
            default:
                    return false;
        }
    }

    /**
     * editOrder
     * Creates the interactive dialog for operations related to crearting or
     * editing the current order
     */
    private void editOrder() {
        System.out.println("editOrder");
        // Open Order Menu
        OrderMenu.getInstance().begin();
    }

    /**
     * payOrder
     * Pays for the current order
     */
    private void payOrder() {
        System.out.println("payOrder");


        // Get money
        Money payment;
        try {
            
            System.out.print("How much money do you have in EUR? ");
            double input = scan.nextDouble();
            payment = Money.of(input);
            if (payment.minus(order.totalWithTax(10)).getAmount().doubleValue() < 0) 
                throw new IllegalArgumentException("Inssufficent Funds\nOrder Total: " + order.totalWithTax(10));

        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return;
        }
        
        try {
            
            // Select Payment Method
            System.out.print("How Would you like to pay?\n" +
                             "1. Cash\n"+   
                             "2. Card\n"+   
                             "3. Wallet\n"+   
                            "");
            int input = scan.nextInt();
            if (input < 1 || input > 3)
                throw new IllegalArgumentException("You entered: " + input +
                                                   "You must enter an option from 1 to 3");

            switch (input) {

                // Pay
                case 1: 
                        order.pay(new CashPayment());
                        break;
                case 2: System.out.print("Enter your card number: ");
                        String cardNumber = scan.next();
                        order.pay(new CardPayment(cardNumber));
                        break;
                case 3: System.out.print("Enter your wallet id: ");
                        String walletId = scan.next();
                        order.pay(new WalletPayment(walletId));
                        break;

            }

        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return;
        }

        // Change
        System.out.println("Your change is: " + payment.minus(order.totalWithTax(10)));

        // Setup new order
        order = new Order(OrderIds.next());
    }

    /**
     * stockStore
     * Provides a menu for a store employee to add items to their catalog
     */
    private void stockStore() {
        System.out.println("stockStore");
        // Base Product
        System.out.println("Select base product");
        catalog.toString();


        // Create Product Decorator
        //Product product = new SimpleProduct(productID, productName, cost);
        
        // Add item to Catalog
        //catalog.add(new SimpleProduct("P-ESP", "Espresso", Money.of(2.50)));

    }
}
