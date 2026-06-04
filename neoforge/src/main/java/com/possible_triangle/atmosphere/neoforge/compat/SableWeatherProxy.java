package com.possible_triangle.atmosphere.neoforge.compat;

import com.possible_triangle.atmosphere.api.v1.*;
import com.possible_triangle.atmosphere.api.v1.events.AtmosphereEvents;
import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.companion.SubLevelAccess;
import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.world.level.Level;

public class SableWeatherProxy implements LevelWeatherProxy {

    public static void register() {
        AtmosphereEvents.REGISTER_WEATHER_PROXY.subscribe(proxies ->
            proxies.add(SableWeatherProxy::new)
        );
    }

    private final WeatherProvider weather;

    private SableWeatherProxy(WeatherProvider weather) {
        this.weather = weather;
    }

    @Override
    public Optional<Holder<WeatherCondition>> atPosition(Level level, Position pos) {
        var outside = SableCompanion.INSTANCE.projectOutOfSubLevel(level, pos);

        return weather.atPosition(level, outside)
            .or(() -> insideSubLevels(level, outside));
    }

    private Optional<Holder<WeatherCondition>> insideSubLevels(Level level, Position pos) {
        var mutablePos = JOMLConversion.toJOML(pos);

        var bounds = new BoundingBox3d(pos, pos).expand(10);
        var subLevels = SableCompanion.INSTANCE.getAllIntersecting(level, bounds);
        for (var subLevel : subLevels) {
            mutablePos.set(pos.x(), pos.y(), pos.z());

            subLevel.logicalPose().transformPositionInverse(mutablePos);
            var match = insideSubLevel(subLevel, level, JOMLConversion.toMojang(mutablePos));
            if (match.isPresent()) return match;
        }

        return Optional.empty();
    }

    private Optional<Holder<WeatherCondition>> insideSubLevel(SubLevelAccess subLevel, Level level, Position pos) {
        return weather.atPosition(level, pos);
    }

}
