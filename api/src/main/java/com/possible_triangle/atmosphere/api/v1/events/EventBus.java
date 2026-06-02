package com.possible_triangle.atmosphere.api.v1.events;

import java.util.function.Consumer;

public interface EventBus<T> {

    void dispatch(T event);

    Runnable subscribe(Consumer<T> listener);

}
