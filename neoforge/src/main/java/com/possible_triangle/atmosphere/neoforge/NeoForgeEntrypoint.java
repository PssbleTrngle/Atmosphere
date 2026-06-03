package com.possible_triangle.atmosphere.neoforge;

import com.possible_triangle.atmosphere.api.v1.AtmosphereConstants;
import com.possible_triangle.atmosphere.api.v1.AtmosphereRegistries;
import com.possible_triangle.atmosphere.api.v1.WeatherCondition;
import com.possible_triangle.atmosphere.client.DebugRendering;
import com.possible_triangle.atmosphere.command.AtmosphereCommand;
import com.possible_triangle.atmosphere.impl.ServerLevelWeather;
import com.possible_triangle.atmosphere.impl.WeatherApiImpl;
import com.possible_triangle.atmosphere.neoforge.client.NeoForgeDebugRendering;
import com.possible_triangle.atmosphere.neoforge.compat.SableWeatherProxy;
import com.possible_triangle.atmosphere.network.AtmosphereNetwork;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@Mod(AtmosphereConstants.MOD_ID)
@EventBusSubscriber
public class NeoForgeEntrypoint {

    public NeoForgeEntrypoint(IEventBus modBus) {
        if (AtmosphereNetwork.INSTANCE instanceof NeoForgeNetwork network) {
            network.register(modBus);
        }

        if (WeatherApiImpl.INSTANCE instanceof WeatherApiImpl apiImpl) {
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
        } else {
            AtmosphereConstants.LOGGER.warn("WeatherAPI was overridden by another mod");
        }

        if (DebugRendering.INSTANCE instanceof NeoForgeDebugRendering rendering) {
            NeoForge.EVENT_BUS.addListener((LevelTickEvent.Pre event) -> {
                var level = event.getLevel();
                if (level.isClientSide()) rendering.tick(level);
            });
        }

        if (ModList.get().isLoaded("sable")) {
            SableWeatherProxy.register();
        }
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
