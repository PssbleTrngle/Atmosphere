package com.possible_triangle.atmosphere.api.v1;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public record WeatherCondition(boolean precipitation) {

    public static final Codec<WeatherCondition> CODEC = RecordCodecBuilder.create(builder ->
        builder.group(
            Codec.BOOL.optionalFieldOf("precipitation", false).forGetter(WeatherCondition::precipitation)
        ).apply(builder, WeatherCondition::new)
    );

    public static StreamCodec<RegistryFriendlyByteBuf, WeatherCondition> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL, WeatherCondition::precipitation,
        WeatherCondition::new
    );

    public static final ResourceKey<WeatherCondition> RAIN = builtin("rain");
    public static final ResourceKey<WeatherCondition> SUNNY = builtin("sunny");
    public static final ResourceKey<WeatherCondition> THUNDER_STORM = builtin("thunder_storm");
    public static final ResourceKey<WeatherCondition> SNOW = builtin("thunder_storm");

    @SuppressWarnings("UnnecessaryDefault")
    public static ResourceKey<WeatherCondition> from(Biome.Precipitation precipitation) {
        return switch (precipitation) {
            case NONE -> SUNNY;
            case RAIN -> RAIN;
            case SNOW -> SNOW;
            default ->
                // TODO add API to register these if any mods extend the enum
                throw new IllegalArgumentException("unknown precipitation '%s'".formatted(precipitation));
        };
    }

    private static ResourceKey<WeatherCondition> builtin(String name) {
        var registry = ResourceKey.<WeatherCondition>createRegistryKey(AtmosphereConstants.createId("weather_condition"));
        return ResourceKey.create(registry, ResourceLocation.withDefaultNamespace(name));
    }

}
