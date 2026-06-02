package com.possible_triangle.atmosphere.neoforge;

import com.possible_triangle.atmosphere.network.AtmosphereNetwork;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NeoForgeNetwork implements AtmosphereNetwork {

    private final List<Consumer<PayloadRegistrar>> CONSUMERS = new ArrayList<>();

    void register(IEventBus modBus) {
        modBus.addListener((RegisterPayloadHandlersEvent event) -> {
            var registrar = event.registrar(VERSION);
            CONSUMERS.forEach(it -> it.accept(registrar));
        });
    }

    @Override
    public <T extends CustomPacketPayload> ServerToClient<T> serverToClient(
        CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, T> type,
        ClientHandler<T> receiver
    ) {
        CONSUMERS.add(registrar -> {
            registrar.playToClient(type.type(), type.codec(), (message, context) -> {
                context.enqueueWork(() -> receiver.receive(message, context.player()));
            });
        });

        return PacketDistributor::sendToPlayer;
    }

}
