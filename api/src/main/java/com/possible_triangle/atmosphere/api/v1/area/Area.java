package com.possible_triangle.atmosphere.api.v1.area;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.world.phys.Vec3;

public interface Area {

    boolean contains(Position pos);

    default boolean contains(BlockPos pos) {
        return contains(Vec3.atCenterOf(pos));
    }

}
