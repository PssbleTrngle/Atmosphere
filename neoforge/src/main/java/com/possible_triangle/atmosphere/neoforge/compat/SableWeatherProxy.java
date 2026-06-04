package com.possible_triangle.atmosphere.neoforge.compat;

import com.possible_triangle.atmosphere.api.v1.*;
import com.possible_triangle.atmosphere.api.v1.events.AtmosphereEvents;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;

import java.util.Optional;
import java.util.stream.StreamSupport;

import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import net.createmod.catnip.outliner.Outliner;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
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
    public Optional<Holder<WeatherCondition>> atPosition(Level level, Position pos) {
        var container = SubLevelContainer.getContainer(level);
        if (container == null) return Optional.empty();

        var outside = SableCompanion.INSTANCE.projectOutOfSubLevel(level, pos);

        var inside = SableCompanion.INSTANCE.runIncludingSubLevels(level, pos, false, null, (subLevel, blockPos) -> {
            return blockPos.getCenter();
        });

        if (inside != null) {
            AtmosphereConstants.LOGGER.info("projected inside {}/{} -> {}/{}", pos.x(), pos.z(), inside.x(), inside.z());
        }

        if (outside.equals(pos)) {
            var bounds = new BoundingBox3d(pos, pos).expand(10);
            //var intersecting = StreamSupport.stream(SableCompanion.INSTANCE.getAllIntersecting(level, bounds).spliterator(), false);
        } else {
            AtmosphereConstants.LOGGER.info("projected outside {}/{} -> {}/{}", pos.x(), pos.z(), outside.x(), outside.z());
        }

        Outliner.getInstance()
            .chaseAABB(pos, new AABB(BlockPos.containing(pos)));

        for (var it : container.getAllSubLevels()) {
            Outliner.getInstance()
                .chaseAABB(it, it.boundingBox().toMojang());
        }

        return Optional.empty();
    }

}
