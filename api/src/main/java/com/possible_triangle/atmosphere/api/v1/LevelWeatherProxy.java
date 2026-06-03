package com.possible_triangle.atmosphere.api.v1;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;

public interface LevelWeatherProxy {

    Optional<Holder<WeatherCondition>> atPosition(Level level, BlockPos pos);

}
