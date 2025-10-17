package com.cafepos.demo;

// java
import java.util.HashMap;
import java.util.Scanner;
import java.util.Optional;
// cafepos
import com.cafepos.common.Money;
import com.cafepos.domain.Product;
import com.cafepos.decorator.Syrup;
import com.cafepos.decorator.ExtraShot;
import com.cafepos.decorator.SizeLarge;
import com.cafepos.decorator.OatMilk;
import com.cafepos.domain.LineItem;

public class OrderMenu extends Menu {

    private static OrderMenu menuInstance;

    private OrderMenu(){
        super("Order Menu");
        addMenuItem("Add Line Item"); // 1
        addMenuItem("Get Order"); // 2
        addMenuItem("Mark Ready"); // 3
    }

    /**
     * getInstance
     * If there is an existing instance of CafeMenu it returns it
     * otherwise it will create an instance before returning it.
     */
    public static OrderMenu getInstance(){

        if (menuInstance == null) {
            menuInstance = new OrderMenu();
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
            case 1: addItem();
                    return false;
            case 2: getOrder();
                    return false;
            case 3: markReady();
                    return false;
            default:
                    return false;
        }
    }

    /**
     * addItem
     * adds an item to the current order
     */
    private static void addItem() {

        // Display items in catalog
        catalog.toString();

        // Select an item from the catalog
        String choice = scan.next();
        Optional<Product> p = catalog.findById(choice);
        Product product = p.get();

        if (p.isPresent()) {

            // Add options
            boolean next = true;
            while (next) {

                System.out.println("Choose Any options you want to add");
                System.out.println("Size Large --------------------- 1");
                System.out.println("Oat Milk ----------------------- 2");
                System.out.println("Syrup -------------------------- 3");
                System.out.println("Extra Shot --------------------- 4\n");
                System.out.println("Quit --------------------------- 5\n");

                System.out.print("Enter your selections: ");
                int selection = scan.nextInt();
                if (selection < 0 || selection > 4)
                    throw new IllegalArgumentException("Invalid Selection\nMust be from 1 - 4");

                switch (selection) {
                    case 1: product = new SizeLarge(product);
                            break;
                    case 2: product = new OatMilk(product);
                            break;
                    case 3: product = new Syrup(product);
                            break;
                    case 4: product = new ExtraShot(product);
                            break;
                    case 5: next = false;
                            break;
                }

            }

            // Select the desired quantity of the item
            int quantity = scan.nextInt();

            try {
                // Create Line Item and add to Order
                order.addItem(new LineItem(product, quantity));
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }

        } else 
            System.out.println("Error the requested product: " + choice + "\n Is not currently in stock");
        

    }

    /**
     * getOrder
     * Prints all information relevant to this order
     */
    private static void getOrder() {
        order.toString();
    }

    /**
     * markReady
     * Marks this order as ready
     */
    private static void markReady() {
        order.markReady();
    }

}
