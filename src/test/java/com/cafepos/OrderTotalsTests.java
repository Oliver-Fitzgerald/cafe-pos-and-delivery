package com.cafepos;

import com.cafepos.common.Money;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.SimpleProduct;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTotalsTests {

    @Test
    void order_totals() {
        var p1 = new SimpleProduct("A", "Product A", Money.of(2.50));
        var p2 = new SimpleProduct("B", "Product B", Money.of(3.50));

        var o = new Order(1);
        o.addItem(new LineItem(p1, 2)); // 2 * 2.50 = 5.00
        o.addItem(new LineItem(p2, 1)); // 1 * 3.50 = 3.50

        assertEquals(Money.of(8.50), o.subtotal());
        assertEquals(Money.of(0.85), o.taxAtPercent(10));
        assertEquals(Money.of(9.35), o.totalWithTax(10));
    }

    @Test
    void empty_order() {
        var o = new Order(1);
        assertEquals(Money.zero(), o.subtotal());
        assertEquals(Money.zero(), o.taxAtPercent(10));
        assertEquals(Money.zero(), o.totalWithTax(10));
    }

    @Test
    void order_with_multiple_items() {
        var p1 = new SimpleProduct("ESP", "Espresso", Money.of(2.50));

        var o = new Order(1);
        o.addItem(new LineItem(p1, 1));
        o.addItem(new LineItem(p1, 1));
        o.addItem(new LineItem(p1, 1));

        assertEquals(Money.of(7.50), o.subtotal());
        assertEquals(3, o.items().size());
    }

    @Test
    void line_item_total() {
        var product = new SimpleProduct("P1", "Product 1", Money.of(5.00));
        var lineItem = new LineItem(product, 3);

        assertEquals(Money.of(15.00), lineItem.lineTotal());
    }

    @Test
    void line_item_invalid_quantity() {
        var product = new SimpleProduct("P1", "Product 1", Money.of(5.00));

        assertThrows(IllegalArgumentException.class, () -> new LineItem(product, 0));
        assertThrows(IllegalArgumentException.class, () -> new LineItem(product, -1));
    }

    @Test
    void line_item_null_product() {
        assertThrows(IllegalArgumentException.class, () -> new LineItem(null, 1));
    }
}
