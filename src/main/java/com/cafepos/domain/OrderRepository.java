package com.cafepos.domain;

// Java
import java.util.Optional;

/*
 * OrderRepository
 * Does stuff and things
 */
public interface OrderRepository {

    void save(Order order);
    Optional<Order> findById(long id);
}
