package com.cafepos.state;

/*
 * PreparingState
 * The state applied to an order that has beeen paid for and is being
 * prepared by the kitchen
 */
final class PreparingState implements State {

    @Override
    public void pay(OrderFSM ctx) {
        System.out.println("[State] Already paid");
    }

    @Override
    public void prepare(OrderFSM ctx) {
        System.out.println("[State] Still preparing...");
    }

    @Override
    public void markReady(OrderFSM ctx) {
        System.out.println("[State] Ready for pickup");
        ctx.set(new ReadyState());
    }

    @Override
    public void deliver(OrderFSM ctx) {
        System.out.println("[State] Deliver not allowed before ready");
    }

    @Override
    public void cancel(OrderFSM ctx) {
        System.out.println("[State] Cancelled during prep");
        ctx.set(new CancelledState());
    }

    @Override
    public String name() { return "PREPARING"; }
}
