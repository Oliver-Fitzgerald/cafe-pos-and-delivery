package com.cafepos.observers;

// cafepos
import com.cafepos.observers.OrderEvents;
import com.cafepos.domain.Order;

public final class KitchenDisplay implements OrderObserver {

    /**
     * updated
     * Updates the kitchen with a relevant messeage based on the event that has
     * occurred
     * @param order - The order the updated was received for
     * @param eventType - The event that has occured.
     */
    @Override
    public void updated(Order order, OrderEvents eventType) {

        String notification = "[Kitchen] Order " + order.id() + ":";

        switch (eventType) {

            case ITEM_ADDED: System.out.println(notification + " New item added " + order.items().get(order.items().size() - 1).quantity() + "x " + order.items().get(order.items().size() - 1).product().name());
                break;
            case PAID: System.out.println(notification + " payment received.");
                break;
        }
    }
}
