package com.possible_triangle.atmosphere.api;

import com.possible_triangle.atmosphere.impl.WeatherApiImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public interface WeatherAPI extends WeatherProvider {

    WeatherAPI INSTANCE = new WeatherApiImpl();

    void registerGlobal(WeatherProvider provider);

    void addLocal(ResourceLocation id, WeatherProvider provider, AABB area, ProviderHeartbeat heartbeat);

}
