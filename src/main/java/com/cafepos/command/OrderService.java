package com.cafepos.command;

//java
import java.util.ArrayList;
// cafepos
import com.cafepos.domain.Product;
import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.payment.PaymentStrategy;

public final class OrderService {

    private final ProductFactory factory = new ProductFactory();
    private final Order order;

    public OrderService(Order order) { this.order = order; }

    /**
     * addItem
     */
    public LineItem addItem(String recipe, int qty) {

        Product p = factory.create(recipe);
        LineItem item = new LineItem(p, qty);
        order.addItem(item);
        System.out.println("[Service] Added " + p.name() + " x" + qty);
        return item;
    }

    /**
     * removeLastItem
     */
    public void removeLastItem() {

        this.order.removeLastItem();
        System.out.println("[Service] Removed last item");
    }

    /**
     * totalWithTax
     */
    public Money totalWithTax(int percent) {
        return order.totalWithTax(percent);
    }

    /**
     * pay
     */
    public void pay(PaymentStrategy strategy, int taxPercent) {

        var total = order.totalWithTax(taxPercent);
        //System.out.println("[Debug] Total With Tax: " + total);
        strategy.pay(order);
        System.out.println("[Service] Payment processed for total " + total);
    }

    /**
     * order
     */
    public Order order() { return order; }
}
