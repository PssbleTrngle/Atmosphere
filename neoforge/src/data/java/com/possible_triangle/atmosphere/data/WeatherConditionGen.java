package com.possible_triangle.atmosphere.data;

import com.possible_triangle.atmosphere.api.v1.WeatherCondition;
import net.minecraft.data.worldgen.BootstrapContext;

public class WeatherConditionGen {

    public static void bootstrap(BootstrapContext<WeatherCondition> context) {
        context.register(WeatherCondition.RAIN, new WeatherCondition(true));
        context.register(WeatherCondition.THUNDER_STORM, new WeatherCondition(true));
        context.register(WeatherCondition.SUNNY, new WeatherCondition(false));
    }

}
