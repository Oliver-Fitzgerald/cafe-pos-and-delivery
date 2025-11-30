package com.cafepos.ui;

import com.cafepos.application.events.*;
import com.cafepos.infrastructure.Wiring;
import com.cafepos.infrastructure.Wiring.Components;

public final class EventWiringDemo {
    public static void main(String[] args) {

        EventBus bus = new EventBus();
        Components components = Wiring.createDefault();
        OrderController controller = new OrderController(components.repo(), components.checkout());

        // Registers a callback to be executed for the passed event
        bus.on(OrderCreated.class, event -> System.out.println("[UI] order created: " + event.orderId()));
        bus.on(OrderPaid.class, event -> System.out.println("[UI] order paid: " + event.orderId()));

        long id = 4201L;
        controller.createOrder(id);

        //Trigger callbacks with relevant event objects
        bus.emit(new OrderCreated(id));
        // after a payment in your real flow:
        bus.emit(new OrderPaid(id));

    }
}
