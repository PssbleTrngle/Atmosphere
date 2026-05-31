package com.possible_triangle.atmosphere.api;

import com.possible_triangle.atmosphere.impl.WeatherApiImpl;
import net.minecraft.world.phys.AABB;

public interface WeatherAPI extends WeatherProvider {

    WeatherAPI INSTANCE = new WeatherApiImpl();

    void registerGlobal(WeatherProvider provider);

    void addLocal(AABB area, WeatherProvider provider, ProviderHeartbeat heartbeat);

}
