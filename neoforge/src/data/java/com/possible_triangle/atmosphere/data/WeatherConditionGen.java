package com.possible_triangle.atmosphere.data;

import com.possible_triangle.atmosphere.api.v1.WeatherCondition;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome.Precipitation;

public class WeatherConditionGen {

    public static void bootstrap(BootstrapContext<WeatherCondition> context) {
        context.register(WeatherCondition.RAIN, new WeatherCondition(Precipitation.RAIN));
        context.register(WeatherCondition.THUNDER_STORM, new WeatherCondition(Precipitation.RAIN));
        context.register(WeatherCondition.SUNNY, new WeatherCondition(Precipitation.NONE));
        context.register(WeatherCondition.SNOW, new WeatherCondition(Precipitation.SNOW));
    }

}
