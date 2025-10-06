package com.cafepos.demo;

// cafepos
import com.cafepos.domain.*;
import com.cafepos.factory.ProductFactory;
import com.cafepos.decorator.ProductDecorator;
import com.cafepos.decorator.SizeLarge;
import com.cafepos.decorator.OatMilk;
import com.cafepos.decorator.ExtraShot;
import com.cafepos.common.Money;

public final class Week5Demo {

    public static void main(String[] args) {

        System.out.println("\nDemo of stacked products");

        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        ProductDecorator decorated = new SizeLarge(new OatMilk(new ExtraShot(espresso)));
        System.out.println("Expected: decorated.name() => Espresso + Extra Shot + Oat Milk (Large)");
        System.out.println("  Actual: decorated.name() => " + decorated.name());
        System.out.println("Expected: decorated.price() => 2.50 + 0.80 + 0.50 + 0.70 = 4.5");
        System.out.println("  Actual: decorated.price() => " + decorated.price());


        System.out.println("\nWeek 5 Demo");

        ProductFactory factory = new ProductFactory();
        Product p1 = factory.create("ESP+SHOT+OAT");
        Product p2 = factory.create("LAT+L");

        Order order = new Order(OrderIds.next());
        order.addItem(new LineItem(p1, 1));
        order.addItem(new LineItem(p2, 2));

        System.out.println("Order #" + order.id());
        for (LineItem li : order.items()) {
            System.out.println(" - " + li.product().name() + " x" + li.quantity() + " = " + li.lineTotal());
        }

        System.out.println("Subtotal: " + order.subtotal());
        System.out.println("Tax (10%): " + order.taxAtPercent(10));
        System.out.println("Total: " + order.totalWithTax(10));
    }
}
