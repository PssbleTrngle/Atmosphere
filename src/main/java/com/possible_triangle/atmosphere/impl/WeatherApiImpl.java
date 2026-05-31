package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.ProviderHeartbeat;
import com.possible_triangle.atmosphere.api.WeatherAPI;
import com.possible_triangle.atmosphere.api.WeatherCondition;
import com.possible_triangle.atmosphere.api.WeatherProvider;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

public class WeatherApiImpl implements WeatherAPI {

    private static WeatherProvider GLOBAL = new VanillaWeatherProvider();
    private static final Set<LocalWeatherProvider> LOCAL = new HashSet<>();

    public void register() {
        NeoForge.EVENT_BUS.addListener((LevelTickEvent.Post event) -> {
            validate();
        });
    }

    private void validate() {
        LOCAL.removeIf(it ->
            !it.heartbeat()
                .map(ProviderHeartbeat::validate)
                .orElse(false)
        );
    }

    @Override
    public Holder<WeatherCondition> atPosition(Level level, BlockPos pos) {
        return GLOBAL.atPosition(level, pos);
    }

    @Override
    public void registerGlobal(WeatherProvider provider) {
        GLOBAL = provider;
    }

    @Override
    public void addLocal(AABB area, WeatherProvider provider, ProviderHeartbeat heartbeat) {
        LOCAL.add(new LocalWeatherProvider(provider, area, Optional.of(heartbeat)));
    }
}
