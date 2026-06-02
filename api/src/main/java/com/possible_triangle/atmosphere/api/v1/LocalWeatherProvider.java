package com.possible_triangle.atmosphere.api.v1;

import com.possible_triangle.atmosphere.api.v1.area.Area;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public record LocalWeatherProvider(
    ResourceLocation id,
    WeatherProvider provider,
    Area area,
    Optional<ProviderHeartbeat> heartbeat
) {

}
