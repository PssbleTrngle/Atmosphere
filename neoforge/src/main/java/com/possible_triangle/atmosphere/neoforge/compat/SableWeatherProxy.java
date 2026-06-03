package com.possible_triangle.atmosphere.neoforge.compat;

import com.possible_triangle.atmosphere.api.v1.WeatherCondition;
import com.possible_triangle.atmosphere.impl.LevelWeatherProxy;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;

public class SableWeatherProxy implements LevelWeatherProxy {

    @Override
    public Optional<Holder<WeatherCondition>> atPosition(Level level, BlockPos pos) {
        var container = SubLevelContainer.getContainer(level);
        if (container == null) return Optional.empty();

        var subLevel = container.getSubLevel(pos.getX(), pos.getZ());

        return Optional.empty();
    }

}
