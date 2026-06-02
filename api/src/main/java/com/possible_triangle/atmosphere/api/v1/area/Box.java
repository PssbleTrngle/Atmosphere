package com.possible_triangle.atmosphere.api.v1.area;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Box implements Area {

    private final AABB aabb;

    private Box(AABB aabb) {
        this.aabb = aabb;
    }

    public static Box from(AABB aabb) {
        return new Box(aabb);
    }

    @Override
    public boolean contains(Vec3 pos) {
        return aabb.contains(pos);
    }
}
