package com.possible_triangle.atmosphere.api.v1.events;

import com.possible_triangle.atmosphere.api.v1.LevelWeatherProxy;
import com.possible_triangle.atmosphere.api.v1.WeatherProvider;
import java.util.function.Function;

@FunctionalInterface
public interface RegisterWeatherProxy {

    default void add(LevelWeatherProxy proxy) {
        add($ -> proxy);
    }

    void add(Function<WeatherProvider, LevelWeatherProxy> proxy);

}
