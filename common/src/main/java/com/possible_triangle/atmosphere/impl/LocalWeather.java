package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.v1.LocalWeatherProvider;
import com.possible_triangle.atmosphere.api.v1.ProviderHeartbeat;
import com.possible_triangle.atmosphere.api.v1.WeatherCondition;
import com.possible_triangle.atmosphere.api.v1.WeatherProvider;
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

public class LocalWeather implements LevelWeatherProxy {

    private final ServerLevel level;

    public LocalWeather(ServerLevel level) {
        this.level = level;
    }

    private final HashMap<ResourceLocation, LocalWeatherProvider> providers = new HashMap<>();

    @Override
    public Optional<Holder<WeatherCondition>> atPosition(Level level, BlockPos pos) {
        return providers.values().stream()
            .filter(it -> it.area().contains(Vec3.atCenterOf(pos)))
            .findFirst()
            .map(LocalWeatherProvider::provider)
            .map(it -> it.atPosition(level, pos));
    }

    public void serverTick() {
        var invalid = providers.entrySet().stream()
            .filter(it ->
                !it.getValue().heartbeat()
                    .map(heartbeat -> heartbeat.validate(level))
                    .orElse(true)
            )
            .map(Map.Entry::getKey)
            .toList();

        invalid.forEach(providers::remove);
    }

    public boolean add(ResourceLocation id, WeatherProvider provider, Area area, ProviderHeartbeat heartbeat) {
        var added = new LocalWeatherProvider(id, provider, area, Optional.ofNullable(heartbeat));
        var previous = providers.putIfAbsent(id, added);
        if (previous == null) {
            AtmosphereEvents.LOCAL_PROVIDER_ADDED.dispatch(new LocalProviderAdded(level, added));
            return true;
        }

        return false;
    }

    public boolean remove(ResourceLocation id) {
        var removed = providers.remove(id);
        if (removed != null) {
            AtmosphereEvents.LOCAL_PROVIDER_REMOVED.dispatch(new LocalProviderRemoved(level, removed));
            return true;
        }
        return false;
    }

    public Stream<LocalWeatherProvider> list() {
        return providers.values().stream();
    }

}
