package com.cafepos.domain;

//java
import java.util.ArrayList;
import java.util.List;
// cafepos
import com.cafepos.common.Money;
import com.cafepos.payment.PaymentStrategy;
import com.cafepos.observers.OrderPublisher;
import com.cafepos.observers.OrderObserver;
import com.cafepos.observers.OrderEvents;

public final class Order implements OrderPublisher {

    private final long id;
    private final List<LineItem> items = new ArrayList<>();
    private final List<OrderObserver> observers = new ArrayList<>();

    /**
     * Order
     * Constructs a new Order object with an id
     * note: ids do not have to be unique
     * @param id - The id of the order
     */
    public Order(long id) { 
        this.id = id;
    }

    /**
     * items
     * Returns the LineItems that currently compose this order
     */
    public List<LineItem> items() {
        return this.items;
    }

    /**
     * id
     * Returns the id of this order
     */
    public long id() {
        return this.id;
    }

    /**
     * addItem
     * Adds an item to this Order and sends a notfication of the event to subscribers
     * if successfull
     * @param item - The LineItem to be added to the Order
     * @throws IllegalArgumentException - If the quantity of item <= 0
     */
    public void addItem(LineItem item) throws IllegalArgumentException { 
        if (item.quantity() > 0) {
            this.items.add(item);
            notifyObservers(this, OrderEvents.ITEM_ADDED);
        } else
            throw new IllegalArgumentException(String.format("The passed LineItem cannot be added to the order as it's quantity is less than 1 for\nProduct: %s\nQuantity: %d\n",item.product().name(),item.quantity()));
    }

    /**
     * subtotal
     * @return The total cost of all the LineItems currently in this order
     */
    public Money subtotal() {
        return items.stream().map(LineItem::lineTotal).reduce(Money.zero(), Money::add);
    }

    /**
     * taxAtPercent
     * @param precent - The percentage of tax to be calculated
     * @return The tax that would be paid on the total cost of this order at a given tax rate
     */
    public Money taxAtPercent(int percent) { 
        
        Money netTotal = subtotal();
        return netTotal.multiply(percent / 100.0);
    }

    /**
     * totalWithTax
     * Returns the total cost off all of the LineItems in this order with tax included
     * @param precent - The percentage of tax to be calculated
     * @return the toal cost
     */
    public Money totalWithTax(int percent) { 
        return subtotal().add(taxAtPercent(percent));
    }

    /**
     *pay
     * Pays for this Order using the passed payment method.
     * Notifys subscribers of this Order upon successfull payment.
     * @param paymentMethod - The payment method for this order
     * @throws IllegalArgumentException - If paymentMethod is null
     */
    public void pay(PaymentStrategy paymentMethod) throws IllegalArgumentException {
        if (paymentMethod == null) 
            throw new IllegalArgumentException("Payment strategy has not been defined");
        paymentMethod.pay(this);
        notifyObservers(this, OrderEvents.PAID);
    }

    /**
     * register
     * Regesters a new observer to this Order
     * @param oberver - The observer that will subsrcibe to the
     *                  events of this Order
     * @throws IllegalArgumentException - If observer is null
     */
    @Override
    public void register(OrderObserver observer) {
        if (observer == null)
            throw new IllegalArgumentException("observer is null");
        if (!observers.contains(observer))
            observers.add(observer);
    }

    /**
     * unregister
     * Removes an observer from this orders subsrcibers
     * @param observer - The observer that will be removed from the list
     *                   of subscribers of this event.
     */
    @Override
    public void unregister(OrderObserver observer) {
        observers.remove(observer);
    }

    /**
     * notifyObservers
     * Notifys all observers subscribed to this Order of an event that
     * has occured
     * @param eventType - The event that has occured
     */
    @Override
    public void notifyObservers(Order order, OrderEvents eventType) {
        for (OrderObserver observer: this.observers) {
            //System.out.println("UPDATING: " + observer.getClass() + " with " + eventType);
            observer.updated(this, eventType);
        }
    }

    /**
     * markReady
     * Notifies observers of this order that it is ready
     */
    public void markReady() {
        notifyObservers(this, OrderEvents.READY);
    }

    /**
     * removeLastItem
     * removes the last LineItem added to the current order
     */
    public void removeLastItem() {
        if (items.size() > 0)
            items.remove(items.size() -1);
    }
}
