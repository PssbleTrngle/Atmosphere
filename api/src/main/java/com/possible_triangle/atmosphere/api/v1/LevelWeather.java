package com.possible_triangle.atmosphere.api.v1;

import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public interface LevelWeather extends WeatherProvider {

    void registerGlobal(WeatherProvider provider);

    void addLocal(ResourceLocation id, WeatherProvider provider, AABB area, ProviderHeartbeat heartbeat);

    Stream<LocalWeatherProvider> listLocal();

}
