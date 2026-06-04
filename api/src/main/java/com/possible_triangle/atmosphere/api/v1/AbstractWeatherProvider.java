package com.possible_triangle.atmosphere.api.v1;

import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public abstract class AbstractWeatherProvider implements WeatherProvider {

    @Override
    public final Holder<WeatherCondition> atPosition(Level level, Position pos) {
        var conditions = level.registryAccess().lookupOrThrow(AtmosphereRegistries.WEATHER_CONDITION);
        var key = conditionKeyAt(level, pos);
        return conditions.getOrThrow(key);
    }

    protected abstract ResourceKey<WeatherCondition> conditionKeyAt(Level level, Position pos);

}
