package com.possible_triangle.atmosphere.neoforge;

import com.possible_triangle.atmosphere.api.v1.events.AtmosphereEvents;
import com.possible_triangle.atmosphere.api.v1.events.EventBus;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;

public class NeoForgeEvents implements AtmosphereEvents {

    private static class WrappedEvent<T> extends Event {

        private final ResourceLocation id;
        private final T inner;

        public WrappedEvent(ResourceLocation id, T inner) {
            this.id = id;
            this.inner = inner;
        }
    }

    private record Bus<T>(ResourceLocation id) implements EventBus<T> {
        @Override
        public void dispatch(T event) {
            NeoForge.EVENT_BUS.post(new WrappedEvent<>(id, event));
        }

        @Override
        public Runnable subscribe(Consumer<T> listener) {
            @SuppressWarnings("rawtypes")
            Consumer<WrappedEvent> wrappedListener = event -> {
                if (!event.id.equals(id)) return;
                //noinspection unchecked
                listener.accept((T) event.inner);
            };

            NeoForge.EVENT_BUS.addListener(WrappedEvent.class, wrappedListener);

            return () -> NeoForge.EVENT_BUS.unregister(wrappedListener);
        }
    }

    @Override
    public <T> EventBus<T> create(ResourceLocation id) {
        return new Bus<>(id);
    }

}
