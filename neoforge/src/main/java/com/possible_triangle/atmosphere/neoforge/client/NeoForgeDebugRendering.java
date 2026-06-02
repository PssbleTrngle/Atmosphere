package com.possible_triangle.atmosphere.neoforge.client;

import com.possible_triangle.atmosphere.api.v1.LocalWeatherProvider;
import com.possible_triangle.atmosphere.api.v1.WeatherAPI;
import com.possible_triangle.atmosphere.api.v1.area.Box;
import com.possible_triangle.atmosphere.api.v1.events.AtmosphereEvents;
import com.possible_triangle.atmosphere.api.v1.events.LocalProviderAdded;
import com.possible_triangle.atmosphere.api.v1.events.LocalProviderRemoved;
import com.possible_triangle.atmosphere.client.DebugRendering;
import com.possible_triangle.atmosphere.network.message.RenderLocalWeatherProviders;
import java.util.function.Consumer;
import net.createmod.catnip.outliner.Outliner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class NeoForgeDebugRendering implements DebugRendering {

    private static final Outliner OUTLINER = Outliner.getInstance();

    private static boolean renderLocalWeatherProviders = false;

    private final Consumer<LocalProviderAdded> onLocalProviderAdded = event -> {
        addOutline(event.provider());
    };

    private final Consumer<LocalProviderRemoved> onLocalProviderRemoved = event -> {
        removeOutline(event.provider());
    };

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
        AtmosphereEvents.LOCAL_PROVIDER_ADDED.subscribe(onLocalProviderAdded);
        AtmosphereEvents.LOCAL_PROVIDER_REMOVED.subscribe(onLocalProviderRemoved);

        var weather = WeatherAPI.INSTANCE.getWeather(level);
        weather.listLocal().forEach(this::addOutline);
    }

    public void disable(Level level) {
        AtmosphereEvents.LOCAL_PROVIDER_ADDED.unsubscribe(onLocalProviderAdded);
        AtmosphereEvents.LOCAL_PROVIDER_REMOVED.unsubscribe(onLocalProviderRemoved);

        var weather = WeatherAPI.INSTANCE.getWeather(level);
        weather.listLocal().forEach(this::removeOutline);
    }

}
