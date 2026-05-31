package com.possible_triangle.atmosphere.api.v1;

import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public record LocalWeatherProvider(
    ResourceLocation id,
    WeatherProvider provider,
    AABB area,
    Optional<ProviderHeartbeat> heartbeat
) {

}
