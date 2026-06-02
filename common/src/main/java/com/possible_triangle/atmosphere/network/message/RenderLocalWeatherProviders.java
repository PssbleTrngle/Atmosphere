package com.possible_triangle.atmosphere.network.message;

import com.possible_triangle.atmosphere.api.v1.AtmosphereConstants;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.ByIdMap;

public record RenderLocalWeatherProviders(Action action) implements CustomPacketPayload {

    public static final TypeAndCodec<FriendlyByteBuf, RenderLocalWeatherProviders> TYPE = new TypeAndCodec<>(
        new Type<>(AtmosphereConstants.createId("render_local_weather")),
        StreamCodec.composite(
            Action.STREAM_CODEC, RenderLocalWeatherProviders::action,
            RenderLocalWeatherProviders::new
        )
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE.type();
    }

    public enum Action {
        ACTIVATE,
        DEACTIVATE,
        TOGGLE;

        public boolean resolve(boolean current) {
            return switch (this) {
                case ACTIVATE -> true;
                case DEACTIVATE -> false;
                case TOGGLE -> !current;
            };
        }

        public static final IntFunction<Action> BY_ID = ByIdMap.continuous(
            Action::ordinal,
            Action.values(),
            ByIdMap.OutOfBoundsStrategy.ZERO
        );

        public static final StreamCodec<ByteBuf, Action> STREAM_CODEC =
            ByteBufCodecs.idMapper(Action.BY_ID, Action::ordinal);
    }

}
