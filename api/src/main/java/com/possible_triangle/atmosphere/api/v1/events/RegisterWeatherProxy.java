package com.possible_triangle.atmosphere.api.v1.events;

import com.possible_triangle.atmosphere.api.v1.LevelWeatherProxy;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;

@FunctionalInterface
public interface RegisterWeatherProxy {

    default void add(LevelWeatherProxy proxy) {
        add($ -> proxy);
    }

    void add(Function<ServerLevel, LevelWeatherProxy> proxy);

}
