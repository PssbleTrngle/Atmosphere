package com.possible_triangle.atmosphere.api;

import com.possible_triangle.atmosphere.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public interface WeatherAPI extends WeatherProvider {

    WeatherAPI INSTANCE = Services.load(WeatherAPI.class);

    void registerGlobal(WeatherProvider provider);

    void addLocal(ResourceLocation id, WeatherProvider provider, AABB area, ProviderHeartbeat heartbeat);

}
