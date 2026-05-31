package com.possible_triangle.atmosphere.api.v1;

import com.possible_triangle.atmosphere.platform.Services;
import net.minecraft.world.level.Level;

public interface WeatherAPI  {

    WeatherAPI INSTANCE = Services.load(WeatherAPI.class);

    LevelWeather getWeather(Level level);

}
