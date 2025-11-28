package com.cafepos.demo;

// Cafepos
import com.cafepos.menu.*;
import com.cafepos.common.Money;

public final class Week9Demo_Menu {
    public static void main(String[] args) {

        Menu coffee = new Menu(" Coffee ");
        coffee.add(new MenuItem("Espresso", Money.of(2.50), true));
        coffee.add(new MenuItem("Latte (Large)", Money.of(3.90), true));

        Menu drinks = new Menu(" Drinks ");
        drinks.add(coffee);

        Menu desserts = new Menu(" Desserts ");
        desserts.add(new MenuItem("Cheesecake", Money.of(3.50), false));
        desserts.add(new MenuItem("Oat Cookie", Money.of(1.20), true));

        Menu root = new Menu("CAFÉ MENU");
        root.add(drinks);
        root.add(desserts);

        // Print entire menu
        root.print();

        // List vegetarian items only
        System.out.println("\nVegetarian options:");
        for (MenuItem mi : root.vegetarianItems())
            System.out.println(" * " + mi.name() + " = " + mi.price());

    }
}
