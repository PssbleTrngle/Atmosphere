package com.possible_triangle.atmosphere.api.v1.area;

import net.minecraft.core.Position;
import net.minecraft.world.phys.AABB;

public class Box implements Area {

    private final AABB aabb;

    private Box(AABB aabb) {
        this.aabb = aabb;
    }

    public static Box from(AABB aabb) {
        return new Box(aabb);
    }

    @Override
    public boolean contains(Position pos) {
        return aabb.contains(pos.x(), pos.y(), pos.z());
    }

    public AABB aabb() {
        return this.aabb;
    }

}
