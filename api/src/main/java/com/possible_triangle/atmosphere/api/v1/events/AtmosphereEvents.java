package com.possible_triangle.atmosphere.api.v1.events;

import com.possible_triangle.atmosphere.api.v1.AtmosphereConstants;
import com.possible_triangle.atmosphere.platform.Services;
import net.minecraft.resources.ResourceLocation;

public interface AtmosphereEvents {

    AtmosphereEvents INSTANCE = Services.load(AtmosphereEvents.class);

    EventBus<LocalProviderAdded> LOCAL_PROVIDER_ADDED =
        INSTANCE.create(AtmosphereConstants.createId("local_provider_added"));

    EventBus<LocalProviderRemoved> LOCAL_PROVIDER_REMOVED =
        INSTANCE.create(AtmosphereConstants.createId("local_provider_removed"));

    <T> EventBus<T> create(ResourceLocation id);

    static Runnable combine(Runnable... runnables) {
        return () -> {
            for (var it : runnables) it.run();
        };
    }

}
