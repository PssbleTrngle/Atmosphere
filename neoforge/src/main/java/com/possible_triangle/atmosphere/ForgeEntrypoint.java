package com.possible_triangle.atmosphere;

import com.possible_triangle.atmosphere.api.AtmosphereConstants;
import com.possible_triangle.atmosphere.api.AtmosphereRegistries;
import com.possible_triangle.atmosphere.api.WeatherAPI;
import com.possible_triangle.atmosphere.api.WeatherCondition;
import com.possible_triangle.atmosphere.impl.WeatherApiImpl;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@Mod(AtmosphereConstants.MOD_ID)
@EventBusSubscriber
public class ForgeEntrypoint {

    public ForgeEntrypoint() {
        var apiImpl = (WeatherApiImpl) WeatherAPI.INSTANCE;
        NeoForge.EVENT_BUS.addListener((LevelTickEvent.Post event) -> {
            apiImpl.onTick(event.getLevel());
        });
    }

    @SubscribeEvent
    public static void createRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(AtmosphereRegistries.WEATHER_CONDITION, WeatherCondition.CODEC, WeatherCondition.CODEC);
    }
}
