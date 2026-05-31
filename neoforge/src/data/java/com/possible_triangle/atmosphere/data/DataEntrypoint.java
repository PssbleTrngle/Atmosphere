package com.possible_triangle.atmosphere.data;

import com.possible_triangle.atmosphere.api.AtmosphereConstants;
import com.possible_triangle.atmosphere.api.AtmosphereRegistries;
import java.util.Optional;
import java.util.Set;
import net.minecraft.DetectedVersion;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber
public class DataEntrypoint {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var registries = event.getLookupProvider();

        generator.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(
            Component.literal(AtmosphereConstants.MOD_NAME),
            DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES),
            Optional.empty()
        )));

        var registriesBuilder = new RegistrySetBuilder()
            .add(AtmosphereRegistries.WEATHER_CONDITION, WeatherConditionGen::bootstrap);

        generator.addProvider(true, new DatapackBuiltinEntriesProvider(
           output, registries, registriesBuilder, Set.of("minecraft", AtmosphereConstants.MOD_ID)
        ));
    }

}
