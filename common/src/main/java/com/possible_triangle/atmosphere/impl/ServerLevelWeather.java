package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.v1.*;
import com.possible_triangle.atmosphere.api.v1.area.Area;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class ServerLevelWeather implements LevelWeather {

    private WeatherProvider global = new VanillaWeatherProvider();
    private final LocalWeather local;

    public ServerLevelWeather(ServerLevel level) {
        this.local = new LocalWeather(level);
    }

    public void serverTick() {
        local.serverTick();
    }

    @Override
    public Holder<WeatherCondition> atPosition(Level level, BlockPos pos) {
        return local.atPosition(level, pos)
            .orElseGet(() -> global.atPosition(level, pos));
    }

    @Override
    public void registerGlobal(WeatherProvider provider) {
        global = provider;
    }

    @Override
    public boolean addLocal(ResourceLocation id, WeatherProvider provider, Area area, ProviderHeartbeat heartbeat) {
        return local.add(id, provider, area, heartbeat);
    }

    @Override
    public boolean removeLocal(ResourceLocation id) {
        return local.remove(id);
    }

    @Override
    public Stream<LocalWeatherProvider> listLocal() {
        return local.list();
    }

}
