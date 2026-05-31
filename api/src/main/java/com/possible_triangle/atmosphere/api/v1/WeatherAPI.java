package com.possible_triangle.atmosphere.api.v1;

import com.possible_triangle.atmosphere.platform.Services;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public interface WeatherAPI extends WeatherProvider {

    WeatherAPI INSTANCE = Services.load(WeatherAPI.class);

    void registerGlobal(WeatherProvider provider);

    void addLocal(ResourceLocation id, WeatherProvider provider, AABB area, ProviderHeartbeat heartbeat);

    Stream<LocalWeatherProvider> listLocal();

}
