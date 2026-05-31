package com.possible_triangle.atmosphere;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {

    public static final String MOD_ID = "atmosphere";
    public static final String MOD_NAME = "Atmosphere";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static ResourceLocation createId(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
