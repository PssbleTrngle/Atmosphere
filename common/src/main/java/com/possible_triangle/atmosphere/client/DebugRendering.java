package com.possible_triangle.atmosphere.client;

import com.possible_triangle.atmosphere.network.message.RenderLocalWeatherProviders;
import com.possible_triangle.atmosphere.platform.Services;
import net.minecraft.world.entity.player.Player;

public interface DebugRendering {

    DebugRendering INSTANCE = Services.load(DebugRendering.class);

    void receive(RenderLocalWeatherProviders message, Player player);

}
