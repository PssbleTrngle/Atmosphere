package com.possible_triangle.atmosphere.api.v1;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class AtmosphereRegistries {

    public static final ResourceKey<Registry<WeatherCondition>> WEATHER_CONDITION = create("weather_condition");

    private static <T> ResourceKey<Registry<T>> create(String name) {
        return ResourceKey.createRegistryKey(AtmosphereConstants.createId(name));
    }

}
