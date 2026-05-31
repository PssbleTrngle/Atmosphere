package com.possible_triangle.atmosphere.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public abstract class AbstractWeatherProvider implements WeatherProvider {

    @Override
    public final Holder<WeatherCondition> atPosition(Level level, BlockPos pos) {
        var conditions = level.registryAccess().lookupOrThrow(AtmosphereRegistries.WEATHER_CONDITION);
        var key = conditionKeyAt(level, pos);
        return conditions.getOrThrow(key);
    }

    protected abstract ResourceKey<WeatherCondition> conditionKeyAt(Level level, BlockPos pos);

}
