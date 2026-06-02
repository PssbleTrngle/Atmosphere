package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.v1.*;
import com.possible_triangle.atmosphere.api.v1.area.Area;
import com.possible_triangle.atmosphere.api.v1.events.AtmosphereEvents;
import com.possible_triangle.atmosphere.api.v1.events.LocalProviderAdded;
import com.possible_triangle.atmosphere.api.v1.events.LocalProviderRemoved;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ServerLevelWeather implements LevelWeather {

    private final ServerLevel level;

    private static WeatherProvider GLOBAL = new VanillaWeatherProvider();
    private static final HashMap<ResourceLocation, LocalWeatherProvider> LOCAL = new HashMap<>();

    public ServerLevelWeather(ServerLevel level) {
        this.level = level;
    }

    public void serverTick() {
        validate();
    }

    private void validate() {
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
    public boolean addLocal(ResourceLocation id, WeatherProvider provider, Area area, ProviderHeartbeat heartbeat) {
        var added = new LocalWeatherProvider(id, provider, area, Optional.ofNullable(heartbeat));
        var previous = LOCAL.putIfAbsent(id, added);
        if (previous == null) {
            AtmosphereEvents.LOCAL_PROVIDER_ADDED.dispatch(new LocalProviderAdded(level, added));
            return true;
        }

        return false;
    }

    @Override
    public boolean removeLocal(ResourceLocation id) {
        var removed = LOCAL.remove(id);
        if (removed != null) {
            AtmosphereEvents.LOCAL_PROVIDER_REMOVED.dispatch(new LocalProviderRemoved(level, removed));
            return true;
        }
        return false;
    }

    @Override
    public Stream<LocalWeatherProvider> listLocal() {
        return LOCAL.values().stream();
    }

}
