package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.v1.AbstractWeatherProvider;
import com.possible_triangle.atmosphere.api.v1.WeatherCondition;
import com.possible_triangle.atmosphere.api.v1.WeatherProviderWithDefault;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public class VanillaWeatherProvider extends AbstractWeatherProvider implements WeatherProviderWithDefault {

    @Override
    protected Optional<ResourceKey<WeatherCondition>> conditionKeyAt(Level level, Position pos) {
        var blockPos = BlockPos.containing(pos);
        if (level.isRaining()
            && level.canSeeSky(blockPos)
            && pos.y() > level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos).getY()
        ) {
            var biome = level.getBiome(blockPos).value();
            return Optional.of(WeatherCondition.from(biome.getPrecipitationAt(blockPos)));
        }

        return Optional.empty();
    }

    @Override
    public Holder<WeatherCondition> defaultCondition(Level level) {
        return byKey(level, WeatherCondition.SUNNY);
    }

}
