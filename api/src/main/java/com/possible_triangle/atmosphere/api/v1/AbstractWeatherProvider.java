package com.possible_triangle.atmosphere.api.v1;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public abstract class AbstractWeatherProvider implements WeatherProvider {

    @Override
    public final Optional<Holder<WeatherCondition>> atPosition(Level level, Position pos) {
        var key = conditionKeyAt(level, pos);
        return key.map(it -> byKey(level, it));
    }

    protected abstract Optional<ResourceKey<WeatherCondition>> conditionKeyAt(Level level, Position pos);

    protected final Holder<WeatherCondition> byKey(Level level, ResourceKey<WeatherCondition> key) {
        var conditions = level.registryAccess().lookupOrThrow(AtmosphereRegistries.WEATHER_CONDITION);
        return conditions.getOrThrow(key);
    }

}
