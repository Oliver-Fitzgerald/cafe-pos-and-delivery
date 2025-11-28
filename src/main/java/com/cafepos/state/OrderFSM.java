package com.cafepos.state;

/*
 * OrderFSM
 * Delegegates the triggered action to the current state
 */
public final class OrderFSM {

    private State state; // The current state

    public OrderFSM() { this.state = new NewState(); }

    public void set(State s) { this.state = s; }
    public String status() { return state.name(); }

    public void pay() { state.pay(this); }
    public void prepare() { state.prepare(this); }
    public void markReady() { state.markReady(this); }
    public void deliver() { state.deliver(this); }
    public void cancel() { state.cancel(this); }
}
