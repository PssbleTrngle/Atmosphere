package com.possible_triangle.atmosphere.api;

import net.minecraft.world.level.Level;

@FunctionalInterface
public interface ProviderHeartbeat {

    boolean validate(Level level);

}
