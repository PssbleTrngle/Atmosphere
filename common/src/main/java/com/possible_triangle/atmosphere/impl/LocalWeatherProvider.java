package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.ProviderHeartbeat;
import com.possible_triangle.atmosphere.api.WeatherProvider;
import java.util.Optional;
import net.minecraft.world.phys.AABB;

public record LocalWeatherProvider(
    WeatherProvider provider,
    AABB area,
    Optional<ProviderHeartbeat> heartbeat
) {

}
