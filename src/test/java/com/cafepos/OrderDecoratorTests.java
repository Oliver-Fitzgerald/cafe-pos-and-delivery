package com.cafepos;

// junit
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;
// java
import java.util.stream.Stream;
//cafepos
import com.cafepos.domain.Order;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Product;
import com.cafepos.domain.SimpleProduct;
import com.cafepos.decorator.*;
import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;

public class OrderDecoratorTests {

    /**
     * confirmLineItemTotals
     * confirms that when calulating the total of a LineItem wether
     * the product implments Product directly or extends OrderDecorator
     * The result is the same
     */
    @ParameterizedTest
    @MethodSource("confirmLineItemTotalsTestData")
    void lineItemTotals(LineItem item, Money expectedResult) {


        assertEquals(item.lineTotal(), expectedResult);
    }
    static Stream<Arguments> confirmLineItemTotalsTestData() {
        return Stream.of(
            Arguments.of(new LineItem(new SimpleProduct("P-ESP", "Espresso", Money.of(10)),2), Money.of(20)),
            Arguments.of(new LineItem(new SizeLarge(new SimpleProduct("P-ESP", "Espresso", Money.of(10))),3), Money.of(32.1))
        
        );
    }

    @Test
    void decoratorSingleAddon() {

        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product withShot = new ExtraShot(espresso);

        assertEquals("Espresso + Extra Shot", withShot.name());
        assertEquals(Money.of(3.30), ((ProductDecorator) withShot).price());
    }

    @Test void decoratorStacks() {
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product decorated = new SizeLarge(new OatMilk(new ExtraShot(espresso)));

        assertEquals("Espresso + Extra Shot + Oat Milk (Large)", decorated.name());
        assertEquals(Money.of(4.50), ((ProductDecorator) decorated).price());
    }
    @Test void factoryParsesRecipe() {

        ProductFactory factory = new ProductFactory();
        Product product = factory.create("ESP+SHOT+OAT");

        assertTrue(product.name().contains("Espresso") && product.name().contains("Oat Milk"));
    }

    @Test void orderUsesDecoratedPrice() {
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product withShot = new ExtraShot(espresso);

        Order order = new Order(1);
        order.addItem(new LineItem(withShot, 2));

        assertEquals(Money.of(6.60), order.subtotal());
    }

}
