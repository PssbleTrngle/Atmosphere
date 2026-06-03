package com.possible_triangle.atmosphere.neoforge.test_mod.index;

import static com.possible_triangle.atmosphere.neoforge.test_mod.ForgeEntrypoint.REGISTRATE;

import com.possible_triangle.atmosphere.neoforge.test_mod.LocalWeatherBlock;
import com.possible_triangle.atmosphere.neoforge.test_mod.LocalWeatherBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntry;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AtmosphereBlocks {

    public static final BlockEntry<LocalWeatherBlock> WEATHER_BLOCK = REGISTRATE.object("weather_block")
        .block(LocalWeatherBlock::new)
        .properties(BlockBehaviour.Properties::noLootTable)
        .properties(it -> it.strength(-1.0F, 3600000.0F))
        .simpleBlockEntity(LocalWeatherBlockEntity::new)
        .simpleItem()
        .register();

    public static final Supplier<BlockEntityType<?>> WEATHER_BLOCK_ENTITY = WEATHER_BLOCK.getSibling(Registries.BLOCK_ENTITY_TYPE);

    public static void init() {
        // load this class
    }
}
