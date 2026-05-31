package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.v1.LevelWeather;
import com.possible_triangle.atmosphere.api.v1.LocalWeatherProvider;
import com.possible_triangle.atmosphere.api.v1.ProviderHeartbeat;
import com.possible_triangle.atmosphere.api.v1.WeatherProvider;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public class VanillaLevelWeather extends VanillaWeatherProvider implements LevelWeather {

    public static final VanillaLevelWeather INSTANCE = new VanillaLevelWeather();

    @Override
    public void registerGlobal(WeatherProvider provider) {
    }

    @Override
    public void addLocal(ResourceLocation id, WeatherProvider provider, AABB area, ProviderHeartbeat heartbeat) {
    }

    @Override
    public Stream<LocalWeatherProvider> listLocal() {
        return Stream.empty();
    }

}
