package com.possible_triangle.atmosphere.api.v1;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.dimension.DimensionType;

public class AtmosphereTags {

    public static final TagKey<DimensionType> HAS_WEATHER =
        TagKey.create(Registries.DIMENSION_TYPE, AtmosphereConstants.createId("has_weather"));

}
