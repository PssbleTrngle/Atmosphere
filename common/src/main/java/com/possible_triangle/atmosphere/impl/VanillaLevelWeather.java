package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.v1.*;
import com.possible_triangle.atmosphere.api.v1.area.Area;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;

public class VanillaLevelWeather extends VanillaWeatherProvider implements LevelWeather {

    public static final VanillaLevelWeather INSTANCE = new VanillaLevelWeather();

    @Override
    public void registerGlobal(WeatherProviderWithDefault provider) {
    }

    @Override
    public boolean addLocal(ResourceLocation id, WeatherProvider provider, Area area, ProviderHeartbeat heartbeat) {
        return false;
    }

    @Override
    public boolean removeLocal(ResourceLocation id) {
        return false;
    }

    @Override
    public Stream<LocalWeatherProvider> listLocal() {
        return Stream.empty();
    }

}
