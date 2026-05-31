package com.possible_triangle.atmosphere;

import com.possible_triangle.atmosphere.api.v1.AtmosphereConstants;
import com.possible_triangle.atmosphere.api.v1.AtmosphereRegistries;
import com.possible_triangle.atmosphere.api.v1.WeatherAPI;
import com.possible_triangle.atmosphere.api.v1.WeatherCondition;
import com.possible_triangle.atmosphere.command.AtmosphereCommand;
import com.possible_triangle.atmosphere.impl.ServerLevelWeather;
import com.possible_triangle.atmosphere.impl.WeatherApiImpl;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@Mod(AtmosphereConstants.MOD_ID)
@EventBusSubscriber
public class ForgeEntrypoint {

    public ForgeEntrypoint() {
        var apiImpl = (WeatherApiImpl) WeatherAPI.INSTANCE;

        NeoForge.EVENT_BUS.addListener((LevelTickEvent.Post event) -> {
            if (event.getLevel().isClientSide()) return;
            if (apiImpl.getWeather(event.getLevel()) instanceof ServerLevelWeather weather) {
                weather.serverTick();
            }
        });

        NeoForge.EVENT_BUS.addListener((LevelEvent.Load event) -> {
            if (event.getLevel() instanceof Level level) {
                apiImpl.load(level);
            }
        });

        NeoForge.EVENT_BUS.addListener((LevelEvent.Unload event) -> {
            if (event.getLevel() instanceof Level level) {
                apiImpl.unload(level);
            }
        });
    }

    @SubscribeEvent
    public static void createRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(AtmosphereRegistries.WEATHER_CONDITION, WeatherCondition.CODEC, WeatherCondition.CODEC);
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        AtmosphereCommand.register(event.getDispatcher());
    }

}
