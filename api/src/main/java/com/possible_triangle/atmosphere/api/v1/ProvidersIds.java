package com.possible_triangle.atmosphere.api.v1;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class ProvidersIds {

    public static ResourceLocation positioned(BlockPos pos, ResourceLocation type) {
        return type.withSuffix("_" + pos.getX() + "_" + pos.getY() + "_" + pos.getZ());
    }

}
