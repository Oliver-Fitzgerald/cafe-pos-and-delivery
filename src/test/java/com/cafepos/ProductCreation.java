package com.cafepos;
    
//junit
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
//cafepos
import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.decorator.ProductDecorator;
import com.cafepos.decorator.SizeLarge;
import com.cafepos.decorator.OatMilk;
import com.cafepos.decorator.ExtraShot;
import com.cafepos.domain.Product;
import com.cafepos.domain.SimpleProduct;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;

public class ProductCreation {

    public Product decorator;
    public Product factory;

    @BeforeAll
    void setup() {
        factory = new ProductFactory().create("ESP+SHOT+OAT+L");
        decorator = new SizeLarge(new OatMilk(new ExtraShot(new SimpleProduct("P-ESP", "Espresso", Money.of(2.5)))));
    }

    @Test
    void nameEquals() {
        assertEquals(factory.name(), decorator.name());
    }


    @Test
    void unitPriceEquals() {
        ProductDecorator newFactory = (ProductDecorator) factory;
        ProductDecorator newDecorator = (ProductDecorator) decorator;

        assertEquals(newFactory.price(), newDecorator.price());
    }

    @Test
    void orderTotalsEqual() {

        Order order1 = new Order(1);
        order1.addItem(new LineItem(factory, 1));
        Order order2 = new Order(2);
        order2.addItem(new LineItem(decorator, 1));

        assertEquals(order1.subtotal(), order2.subtotal());
        assertEquals(order1.totalWithTax(10), order2.totalWithTax(10));
    }
}
