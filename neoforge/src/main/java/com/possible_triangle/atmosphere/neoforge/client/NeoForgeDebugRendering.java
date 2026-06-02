package com.possible_triangle.atmosphere.neoforge.client;

import com.possible_triangle.atmosphere.api.v1.LocalWeatherProvider;
import com.possible_triangle.atmosphere.api.v1.WeatherAPI;
import com.possible_triangle.atmosphere.api.v1.area.Box;
import com.possible_triangle.atmosphere.api.v1.events.AtmosphereEvents;
import com.possible_triangle.atmosphere.client.DebugRendering;
import com.possible_triangle.atmosphere.network.message.RenderLocalWeatherProviders;
import net.createmod.catnip.outliner.Outliner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class NeoForgeDebugRendering implements DebugRendering {

    private static final Outliner OUTLINER = Outliner.getInstance();

    private static boolean renderLocalWeatherProviders = false;

    private Runnable unregisterCallback = null;

    private void addOutline(LocalWeatherProvider provider) {
        if (provider.area() instanceof Box box) {
            OUTLINER.showAABB(provider, box.aabb())
                .colored(0x5990e3);
        }
    }

    private void removeOutline(LocalWeatherProvider provider) {
        OUTLINER.remove(provider);
    }

    public void receive(RenderLocalWeatherProviders message, Player player) {
        renderLocalWeatherProviders = message.action().resolve(renderLocalWeatherProviders);

        if (renderLocalWeatherProviders) enable(player.level());
        else disable(player.level());
    }

    public void enable(Level level) {
        unregisterCallback = AtmosphereEvents.combine(
            AtmosphereEvents.LOCAL_PROVIDER_ADDED.subscribe(event -> addOutline(event.provider())),
            AtmosphereEvents.LOCAL_PROVIDER_REMOVED.subscribe(event -> removeOutline(event.provider()))
        );

        var weather = WeatherAPI.INSTANCE.getWeather(level);
        weather.listLocal().forEach(this::addOutline);
    }

    public void disable(Level level) {
        if (unregisterCallback != null) unregisterCallback.run();

        var weather = WeatherAPI.INSTANCE.getWeather(level);
        weather.listLocal().forEach(this::removeOutline);
    }

}
