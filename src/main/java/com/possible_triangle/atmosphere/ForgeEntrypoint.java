package com.possible_triangle.atmosphere;

import com.possible_triangle.atmosphere.api.AtmosphereRegistries;
import com.possible_triangle.atmosphere.api.WeatherAPI;
import com.possible_triangle.atmosphere.api.WeatherCondition;
import com.possible_triangle.atmosphere.impl.WeatherApiImpl;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@Mod(Constants.MOD_ID)
@EventBusSubscriber
public class ForgeEntrypoint {

    @SubscribeEvent
    public static void createRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(AtmosphereRegistries.WEATHER_CONDITION, WeatherCondition.CODEC, WeatherCondition.CODEC);

        ((WeatherApiImpl) WeatherAPI.INSTANCE).register();
    }

}
