package com.possible_triangle.atmosphere.api.v1;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.world.level.Level;

public interface WeatherProvider {

    Optional<Holder<WeatherCondition>> atPosition(Level level, Position pos);

}
