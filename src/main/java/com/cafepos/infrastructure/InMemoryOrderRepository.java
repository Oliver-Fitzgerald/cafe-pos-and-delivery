package com.cafepos.infrastructure;

// Cafepos
import com.cafepos.domain.*;
// Java
import java.util.*;

/*
 * InMemoryOrderRepository
 * A stand in for a persistent database. Dependant on domain layer, but the opposite
 * is not true, allowing the type persisstentce mechanism used to be easily swapped out.
 */
public final class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, Order> store = new HashMap<>();

    /**
     * save
     * Saves an order to memory
     * @param order the order to be stored
     */
    @Override
    public void save(Order order) {
        store.put(order.id(), order);
    }

    /**
     * findById
     * retreives the specified order from memory if present by it's id
     * @param id the id coresponding to the order
     */
    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }
}
