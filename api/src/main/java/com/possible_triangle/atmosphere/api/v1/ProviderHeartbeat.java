package com.possible_triangle.atmosphere.api.v1;

import net.minecraft.server.level.ServerLevel;

@FunctionalInterface
public interface ProviderHeartbeat {

    boolean validate(ServerLevel level);

}
