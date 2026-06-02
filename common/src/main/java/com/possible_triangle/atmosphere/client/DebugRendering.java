package com.possible_triangle.atmosphere.client;

import com.possible_triangle.atmosphere.network.message.RenderLocalWeatherProviders;

public class DebugRendering {

    private static boolean renderLocalWeatherProviders = false;

    public static void receive(RenderLocalWeatherProviders message) {
        renderLocalWeatherProviders = message.action().resolve(renderLocalWeatherProviders);
    }

}
