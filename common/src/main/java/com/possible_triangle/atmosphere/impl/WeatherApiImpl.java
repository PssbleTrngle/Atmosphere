package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.v1.*;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class WeatherApiImpl implements WeatherAPI {

    // TODO move to persisted data?
    private final Map<ResourceKey<Level>, LevelWeather> perLevel = new HashMap<>();

    public void load(Level level) {
        if (!isSupported(level)) return;

        if (level instanceof ServerLevel serverLevel) {
            perLevel.put(level.dimension(), new ServerLevelWeather(serverLevel));
        }

        // TODO network sync & co
    }

    public void unload(Level level) {
        perLevel.remove(level.dimension());
    }

    private boolean isSupported(Level level) {
        // TODO tag
        return level.dimension() == Level.OVERWORLD;
    }

    @Override
    public LevelWeather getWeather(Level level) {
        return perLevel.getOrDefault(level.dimension(), VanillaLevelWeather.INSTANCE);
    }

}
