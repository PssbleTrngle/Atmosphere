package com.possible_triangle.atmosphere.api.v1;

import com.possible_triangle.atmosphere.api.v1.area.Area;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public interface LevelWeather extends WeatherProvider {

    void registerGlobal(WeatherProvider provider);

    default boolean addLocal(ResourceLocation id, WeatherProvider provider, Area area) {
        return addLocal(id, provider, area, null);
    }

    boolean addLocal(ResourceLocation id, WeatherProvider provider, Area area, @Nullable ProviderHeartbeat heartbeat);

    boolean removeLocal(ResourceLocation id);

    Stream<LocalWeatherProvider> listLocal();

}
