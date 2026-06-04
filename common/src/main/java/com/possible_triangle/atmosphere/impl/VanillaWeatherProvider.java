package com.possible_triangle.atmosphere.impl;

import com.possible_triangle.atmosphere.api.v1.AbstractWeatherProvider;
import com.possible_triangle.atmosphere.api.v1.WeatherCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public class VanillaWeatherProvider extends AbstractWeatherProvider {

    @Override
    protected ResourceKey<WeatherCondition> conditionKeyAt(Level level, Position pos) {
        var blockPos = BlockPos.containing(pos);
        if (level.isRaining()
            && level.canSeeSky(blockPos)
            && pos.y() > level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos).getY()
        ) {
            var biome = level.getBiome(blockPos).value();
            return WeatherCondition.from(biome.getPrecipitationAt(blockPos));
        }

        return WeatherCondition.SUNNY;
    }

}
