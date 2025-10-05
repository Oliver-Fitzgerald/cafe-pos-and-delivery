package com.cafepos;

// java
import java.util.List;
import java.util.ArrayList;
import java.util.function.BiFunction;
// junit
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
// cafepos
import com.cafepos.domain.Order;
import com.cafepos.domain.Product;
import com.cafepos.domain.SimpleProduct;
import com.cafepos.domain.LineItem;
import com.cafepos.observers.OrderObserver;
import com.cafepos.common.Money;
import com.cafepos.payment.CardPayment;
import com.cafepos.observers.OrderEvents;

public class NotificationTests {

    public static Product product;
    public static Order order;
    public static OrderObserver mockObserver;
    public static List<OrderEvents> events; // mockObserver records events to this list

    @BeforeAll
    static void setupAll(){

        //Initalize Test Objects
        product = new SimpleProduct("A", "A", Money.of(2));
        order = new Order(1);
        order.addItem(new LineItem(product, 1));

        //Initalize Mock Observer
        mockObserver = (anOrder, anEvent) -> events.add(anEvent);
        order.register(mockObserver);

    }

    @BeforeEach
    void setupEach(){

        events = new ArrayList<>(); 
    }
    /**
     * oberversNotifiedOnPay
     * Calling pay notifies us with PAID if paid
     */
    @Test void observersNotifiedOnPay() {

        // Trigger event
        order.pay(new CardPayment("0909 0909 0909 1234"));
        // Assert that event was recorded
        assertTrue(events.contains(OrderEvents.PAID));
    }

    /**
     * oberversNotifiedOnMarkReady
     * Calling markReady notifies us with READY
     */
    @Test void observersNotifiedOnMarkReady() {

        // Trigger event
        order.markReady();
        // Assert that event was recorded
        assertTrue(events.contains(OrderEvents.READY));
    }

    /**
     * oberversNotifiedOnItemAdd
     * Calling addItem notifies us with ITEM_ADDED if item added
     */
    @Test void observersNotifiedOnItemAdd() {

        // Trigger event
        order.addItem(new LineItem(product, 1));
        // Assert that event was recorded
        assertTrue(events.contains(OrderEvents.ITEM_ADDED));
    }

    /**
     * duplicateObserverCheck
     * Ensures duplicate Observers cannot be added to a Publisher
     */
    @Test void duplicateObserverCheck() {

        // Register duplicate mock observer
        order.register(mockObserver);
        // Trigger event
        order.addItem(new LineItem(product, 1));
        // Assert that event was recorded
        assertTrue(events.size() == 1);

    }
}
