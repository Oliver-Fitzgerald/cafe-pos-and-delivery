package com.cafepos.application.events;

// Java
import java.util.*;
import java.util.function.Consumer;

/*
 * EventBus
 * Central location for registering, propogating and subscribing to events
 */
public final class EventBus {

    // Map of events (class<?>) to the registered callbacks to be executed for that event (List<Consumer<?>)
    private final Map<Class<?>, List<Consumer<?>>> handlers = new HashMap<>();

    /**
     * on
     * Adds a callback for a given event type
     */
    public <T> void on(Class<T> event, Consumer<T> callback) {
        handlers.computeIfAbsent(event, key -> new ArrayList<>()).add(callback);
    }

    /**
     * emit
     * Triggers an event with the passed even object for all registered callbacks
     */
    @SuppressWarnings("unchecked")
    public <T> void emit(T event) {
        var list = handlers.getOrDefault(event.getClass(), List.of());
        for (var callback : list) 
            ((Consumer<T>) callback).accept(event);
    }
}
