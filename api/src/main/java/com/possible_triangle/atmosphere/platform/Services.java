package com.possible_triangle.atmosphere.platform;

import java.util.ServiceLoader;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class Services {

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz, Services.class.getClassLoader())
            .findFirst()
            .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }

}
