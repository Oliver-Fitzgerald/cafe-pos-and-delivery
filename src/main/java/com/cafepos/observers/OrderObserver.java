package com.cafepos.observers;

// cafepos
import com.cafepos.domain.Order;

public interface OrderObserver {

    void updated(Order order, OrderEvents eventType);
}
