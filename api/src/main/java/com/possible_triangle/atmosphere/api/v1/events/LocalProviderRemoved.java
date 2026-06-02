package com.possible_triangle.atmosphere.api.v1.events;

import com.possible_triangle.atmosphere.api.v1.LocalWeatherProvider;
import net.minecraft.world.level.Level;

public record LocalProviderRemoved(
    Level level,
    LocalWeatherProvider provider
) {
}
