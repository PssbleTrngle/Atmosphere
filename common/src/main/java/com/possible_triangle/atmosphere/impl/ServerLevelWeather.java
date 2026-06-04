package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.v1.*;
import com.possible_triangle.atmosphere.api.v1.area.Area;
import com.possible_triangle.atmosphere.api.v1.events.AtmosphereEvents;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class ServerLevelWeather implements LevelWeather {

    private WeatherProvider global = new VanillaWeatherProvider();
    private final Set<LevelWeatherProxy> proxies = new HashSet<>();
    private final LocalWeather local;

    public ServerLevelWeather(ServerLevel level) {
        this.local = new LocalWeather(level);
        proxies.add(local);
        AtmosphereEvents.REGISTER_WEATHER_PROXY.dispatch(factory ->
            proxies.add(factory.apply(level))
        );
    }

    public void serverTick() {
        local.serverTick();
    }

    @Override
    public Holder<WeatherCondition> atPosition(Level level, Position pos) {
        return proxies.stream()
            .map(it -> it.atPosition(level, pos))
            .filter(Optional::isPresent)
            .findFirst()
            .flatMap(Function.identity())
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
