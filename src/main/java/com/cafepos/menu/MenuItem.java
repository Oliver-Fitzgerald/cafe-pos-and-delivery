package com.cafepos.menu;

// cafepos
import com.cafepos.common.Money;
// java
import java.util.Collections;
import java.util.Iterator;

/*
 * MenuItem
 * The leaf node of the composite Menu pattern
 */
public final class MenuItem extends MenuComponent {

    private final String name;
    private final Money price;
    private final boolean vegetarian;

    /**
     * MenuItem
     * Constructs menu items with a name price and vegetarian flag
     * @param name The name of the menu item
     * @param price The price of the menu item
     * @param vegetarian A flag to indicate wether the menu item is or is not vegetarian
     */
    public MenuItem(String name, Money price, boolean vegetarian) {

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name required");

        if (price == null) 
            throw new IllegalArgumentException("price required");

        this.name = name; this.price = price; this.vegetarian = vegetarian;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Money price() {
        return price;
    }

    @Override
    public boolean vegetarian() {
        return vegetarian;
    }

    @Override
    // Why are we overriding this if its a leaf?
    public Iterator<MenuComponent> iterator() { 
        return Collections.emptyIterator();
    }

    @Override
    public void print() {

        String veg = vegetarian ? " (V)" : "";
        System.out.println(" - " + name + veg + " = " + price);
    }
}
