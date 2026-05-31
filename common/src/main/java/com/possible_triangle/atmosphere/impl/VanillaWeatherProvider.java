package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.v1.AbstractWeatherProvider;
import com.possible_triangle.atmosphere.api.v1.WeatherCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public class VanillaWeatherProvider extends AbstractWeatherProvider {

    @Override
    protected ResourceKey<WeatherCondition> conditionKeyAt(Level level, BlockPos pos) {
        if (level.isRaining()
            && level.canSeeSky(pos)
            && pos.getY() > level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY()
        ) {
            var biome = level.getBiome(pos).value();
            return WeatherCondition.from(biome.getPrecipitationAt(pos));
        }

        return WeatherCondition.SUNNY;
    }

}
