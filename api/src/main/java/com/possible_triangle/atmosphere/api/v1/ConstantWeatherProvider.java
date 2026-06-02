package com.possible_triangle.atmosphere.api.v1;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class ConstantWeatherProvider extends AbstractWeatherProvider {

    private final ResourceKey<WeatherCondition> weatherCondition;

    public ConstantWeatherProvider(ResourceKey<WeatherCondition> weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    @Override
    protected ResourceKey<WeatherCondition> conditionKeyAt(Level level, BlockPos pos) {
        return this.weatherCondition;
    }

}
