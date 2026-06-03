package com.possible_triangle.atmosphere.neoforge.test_mod;

import com.possible_triangle.atmosphere.neoforge.test_mod.index.AtmosphereBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LocalWeatherBlock extends Block implements EntityBlock {

    public LocalWeatherBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return AtmosphereBlocks.WEATHER_BLOCK_ENTITY.get().create(pos, state);
    }

}
