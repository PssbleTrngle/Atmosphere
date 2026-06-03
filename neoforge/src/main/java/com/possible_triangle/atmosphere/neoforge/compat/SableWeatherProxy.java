package com.possible_triangle.atmosphere.neoforge.compat;

import com.possible_triangle.atmosphere.api.v1.ConstantWeatherProvider;
import com.possible_triangle.atmosphere.api.v1.LevelWeatherProxy;
import com.possible_triangle.atmosphere.api.v1.WeatherCondition;
import com.possible_triangle.atmosphere.api.v1.WeatherProvider;
import com.possible_triangle.atmosphere.api.v1.events.AtmosphereEvents;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import java.util.Optional;
import net.createmod.catnip.outliner.Outliner;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SableWeatherProxy implements LevelWeatherProxy {

    private final static WeatherProvider PROVIDER = new ConstantWeatherProvider(WeatherCondition.RAIN);

    public static void register() {
        AtmosphereEvents.REGISTER_WEATHER_PROXY.subscribe(proxies ->
            proxies.add(new SableWeatherProxy())
        );
    }

    @Override
    public Optional<Holder<WeatherCondition>> atPosition(Level level, BlockPos pos) {
        var container = SubLevelContainer.getContainer(level);
        if (container == null) return Optional.empty();

        var blockBounds = new AABB(pos).inflate(10);
        var subLevel = container.getAllSubLevels().stream()
            .filter(it -> it.boundingBox().intersects(blockBounds))
            .findAny();

        for (var it : container.getAllSubLevels()) {
            Outliner.getInstance()
                .chaseAABB(pos, it.boundingBox().toMojang());
        }

        return subLevel.map(it -> PROVIDER.atPosition(it.getLevel(), pos));
    }

}
