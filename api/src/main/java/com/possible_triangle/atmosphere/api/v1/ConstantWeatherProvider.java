package com.possible_triangle.atmosphere.api.v1;

import net.minecraft.core.Position;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class ConstantWeatherProvider extends AbstractWeatherProvider {

    private final ResourceKey<WeatherCondition> weatherCondition;

    public ConstantWeatherProvider(ResourceKey<WeatherCondition> weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    @Override
    protected ResourceKey<WeatherCondition> conditionKeyAt(Level level, Position pos) {
        return this.weatherCondition;
    }

}
