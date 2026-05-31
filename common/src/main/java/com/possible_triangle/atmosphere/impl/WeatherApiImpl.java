package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.ProviderHeartbeat;
import com.possible_triangle.atmosphere.api.WeatherAPI;
import com.possible_triangle.atmosphere.api.WeatherCondition;
import com.possible_triangle.atmosphere.api.WeatherProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class WeatherApiImpl implements WeatherAPI {

    private static WeatherProvider GLOBAL = new VanillaWeatherProvider();
    private static final HashMap<ResourceLocation, LocalWeatherProvider> LOCAL = new HashMap<>();

    public void onTick(Level level) {
        validate(level);
    }

    private void validate(Level level) {
        var invalid = LOCAL.entrySet().stream()
            .filter(it ->
                !it.getValue().heartbeat()
                    .map(heartbeat -> heartbeat.validate(level))
                    .orElse(false)
            )
            .map(Map.Entry::getKey)
            .toList();

        invalid.forEach(LOCAL::remove);
    }

    @Override
    public Holder<WeatherCondition> atPosition(Level level, BlockPos pos) {
        return LOCAL.values().stream()
            .filter(it -> it.area().contains(Vec3.atCenterOf(pos)))
            .findFirst()
            .map(LocalWeatherProvider::provider)
            .orElse(GLOBAL)
            .atPosition(level, pos);
    }

    @Override
    public void registerGlobal(WeatherProvider provider) {
        GLOBAL = provider;
    }

    @Override
    public void addLocal(ResourceLocation id, WeatherProvider provider, AABB area, ProviderHeartbeat heartbeat) {
        LOCAL.putIfAbsent(id, new LocalWeatherProvider(provider, area, Optional.of(heartbeat)));
    }
}
