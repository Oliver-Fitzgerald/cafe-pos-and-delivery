package com.cafepos.domain;

import java.util.ArrayList;
import java.util.List;
import com.cafepos.common.Money;


public final class Order {

    private final long id;
    private final List<LineItem> items = new ArrayList<>();

    public Order(long id) { 
        this.id = id;
    }

    public void addItem(LineItem item) throws IllegalArgumentException { 
        if (item.quantity() > 0)
            this.items.add(item);
        else
            throw new IllegalArgumentException(String.format("The passed LineItem cannot be added to the order as it's quantity is less than 1 for\nProduct: %s\nQuantity: %d\n",item.product().name(),item.quantity()));
    }

    public Money subtotal() {
        return items.stream().map(LineItem::lineTotal).reduce(Money.zero(), Money::add);
    }

    public Money taxAtPercent(int percent) { 
        
        Money netTotal = subtotal();
        return netTotal.multiply(percent / 100.0);
    }

    public Money totalWithTax(int percent) { 
        return subtotal().add(taxAtPercent(percent));
    }

    public List<LineItem> items() {
        return this.items;
    }

    public long id() {
        return this.id;
    }
}
