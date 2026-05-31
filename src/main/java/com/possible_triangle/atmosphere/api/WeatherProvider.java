package com.possible_triangle.atmosphere.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;

public interface WeatherProvider {

    Holder<WeatherCondition> atPosition(Level level, BlockPos pos);

}
