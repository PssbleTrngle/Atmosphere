package com.possible_triangle.atmosphere.neoforge.test_mod;

import com.possible_triangle.atmosphere.api.v1.*;
import com.possible_triangle.atmosphere.api.v1.area.Box;
import com.possible_triangle.atmosphere.neoforge.test_mod.index.AtmosphereBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class LocalWeatherBlockEntity extends BlockEntity {

    public LocalWeatherBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        if (hasLevel()) {
            var weather = WeatherAPI.INSTANCE.getWeather(level);
            var pos = getBlockPos();
            var id = ProvidersIds.positioned(pos, AtmosphereBlocks.WEATHER_BLOCK.getId());
            var area = Box.from(new AABB(pos).inflate(5));
            weather.addLocal(id, new ConstantWeatherProvider(WeatherCondition.RAIN), area);
        }
    }

}
