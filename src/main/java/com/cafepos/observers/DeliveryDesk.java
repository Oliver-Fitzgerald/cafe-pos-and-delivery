package com.cafepos.observers;

// cafepos
import com.cafepos.domain.Order;

public final class DeliveryDesk implements OrderObserver {

    /**
     * updated
     * Updates the delivery desk with a relevant messeage based on the event that has
     * occurred
     * @param order - The order the updated was received for
     * @param eventType - The event that has occured.
     */
    @Override
    public void updated(Order order, OrderEvents eventType) {

        String notification = "[Delivery] Order " + order.id();

        switch (eventType) {

            case READY: System.out.println(notification + " is ready for delivery.");
                break;
        }
    }
}
