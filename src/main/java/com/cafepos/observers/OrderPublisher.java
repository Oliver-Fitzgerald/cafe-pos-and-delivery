package com.cafepos.observers;

// cafepos
import com.cafepos.domain.Order;

public interface OrderPublisher {

    void register(OrderObserver observer);
    void unregister(OrderObserver observer);
    void notifyObservers(Order order, OrderEvents eventType);
}
