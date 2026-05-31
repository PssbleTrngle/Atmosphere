package com.possible_triangle.atmosphere.data;

import com.possible_triangle.atmosphere.api.v1.AtmosphereConstants;
import com.possible_triangle.atmosphere.api.v1.AtmosphereTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;

public class DimensionTagGen extends TagsProvider<DimensionType> {

    protected DimensionTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.DIMENSION_TYPE, registries, AtmosphereConstants.MOD_ID, null);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(AtmosphereTags.HAS_WEATHER).add(BuiltinDimensionTypes.OVERWORLD);
    }

}
