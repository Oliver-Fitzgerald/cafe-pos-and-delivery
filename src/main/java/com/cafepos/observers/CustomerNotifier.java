package com.cafepos.observers;

// cafepos
import com.cafepos.domain.Order;

public final class CustomerNotifier implements OrderObserver {

    /**
     * updated
     * Updates the customer with a relevant messeage based on the event that has
     * occurred
     * @param order - The order the updated was received for
     * @param eventType - The event that has occured.
     */
    @Override
    public void updated(Order order, OrderEvents eventType) {

        String notification = "[Customer] Dear customer, your Order " + order.id() + " has been updated:";

        switch (eventType) {

            case ITEM_ADDED: System.out.println(notification + " Item " + order.items().get(order.items().size() - 1).quantity() + "x " +  order.items().get(order.items().size() - 1).product().name() + " added to order.");
                break;
            case READY: System.out.println(notification + " Your order is ready. :)");
                break;
            case PAID: System.out.println(notification + " Order paid.");
                break;
        }
    }
}
