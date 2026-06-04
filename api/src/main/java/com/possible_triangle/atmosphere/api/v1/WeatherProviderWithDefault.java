package com.possible_triangle.atmosphere.api.v1;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.world.level.Level;

public interface WeatherProviderWithDefault {

    Optional<Holder<WeatherCondition>> atPosition(Level level, Position pos);

    Holder<WeatherCondition> defaultCondition(Level level);

    default Holder<WeatherCondition> atPositionOrDefault(Level level, Position pos) {
        return atPosition(level, pos).orElseGet(() -> defaultCondition(level));
    }

}
