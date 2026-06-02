package com.possible_triangle.atmosphere.data;

import com.possible_triangle.atmosphere.api.v1.AtmosphereConstants;
import com.possible_triangle.atmosphere.command.LocalCommand;
import com.possible_triangle.atmosphere.command.WeatherConditionArgument;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangGen extends LanguageProvider {

    public LangGen(PackOutput output) {
        super(output, AtmosphereConstants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(WeatherConditionArgument.INVALID_WEATHER_CONDITION, "unknown weather condition '%s'");
        add(LocalCommand.LIST_HEADER, "there are %s local weather providers");
        add(LocalCommand.LIST_ENTRY, "%s");
        add(LocalCommand.CREATED, "created local weather provider with ID %s");
        add(LocalCommand.DUPLICATE, "unable to create local weather provider, ID %s is already present");
        add(LocalCommand.REMOVED, "removed local weather provider with ID %s");
        add(LocalCommand.UNKNOWN, "there is no local weather provider with ID %s");
    }

}
