package com.possible_triangle.atmosphere.network;

import com.possible_triangle.atmosphere.client.DebugRendering;
import com.possible_triangle.atmosphere.network.message.RenderLocalWeatherProviders;
import com.possible_triangle.atmosphere.platform.Services;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public interface AtmosphereNetwork {

    String VERSION = "1";

    AtmosphereNetwork INSTANCE = Services.load(AtmosphereNetwork.class);

    ServerToClient<RenderLocalWeatherProviders> RENDER_LOCAL_WEATHER = INSTANCE.serverToClient(
        RenderLocalWeatherProviders.TYPE,
        DebugRendering::receive
    );

    <T extends CustomPacketPayload> ServerToClient<T> serverToClient(
        CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, T> type,
        Consumer<T> receiver
    );

    @FunctionalInterface
    interface ServerToClient<T extends CustomPacketPayload> {
        void sendTo(ServerPlayer player, T message);
    }

}
