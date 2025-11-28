package com.cafepos.state;

/*
 * State
 * Interface for all state declerations
 */
public interface State {

    void pay(OrderFSM ctx);
    void prepare(OrderFSM ctx);
    void markReady(OrderFSM ctx);
    void deliver(OrderFSM ctx);
    void cancel(OrderFSM ctx);
    String name();
}
