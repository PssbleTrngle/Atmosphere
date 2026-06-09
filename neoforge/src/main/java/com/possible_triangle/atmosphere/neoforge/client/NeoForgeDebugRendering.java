package com.possible_triangle.atmosphere.neoforge.client;

import com.possible_triangle.atmosphere.api.v1.AtmosphereConstants;
import com.possible_triangle.atmosphere.api.v1.WeatherAPI;
import com.possible_triangle.atmosphere.api.v1.area.Box;
import com.possible_triangle.atmosphere.client.DebugRendering;
import com.possible_triangle.atmosphere.network.message.RenderLocalWeatherProviders;
import net.createmod.catnip.outliner.Outliner;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLEnvironment;

public class NeoForgeDebugRendering implements DebugRendering {

    public static final String MESSAGE_ENABLED = AtmosphereConstants.MOD_ID + ".debug.render.enabled";
    public static final String MESSAGE_DISABLED = AtmosphereConstants.MOD_ID + ".debug.render.disabled";

    private static final Outliner OUTLINER = Outliner.getInstance();

    // TODO persist to client config file
    private static boolean renderLocalWeatherProviders = !FMLEnvironment.production;

    public void receive(RenderLocalWeatherProviders message, Player player) {
        renderLocalWeatherProviders = message.action().resolve(renderLocalWeatherProviders);
        var notification = renderLocalWeatherProviders ? MESSAGE_ENABLED : MESSAGE_DISABLED;
        player.displayClientMessage(Component.translatable(notification), false);
    }

    public void tick(Level level) {
        if (!renderLocalWeatherProviders) return;

        var weather = WeatherAPI.INSTANCE.getWeather(level);
        var providers = weather.listLocal();

        providers.forEach(provider -> {
            if (provider.area() instanceof Box box) {
                OUTLINER.chaseAABB(provider, box.aabb())
                    .colored(0x5990e3);
            }
        });
    }

}
