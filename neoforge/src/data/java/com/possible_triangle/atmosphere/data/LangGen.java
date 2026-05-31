package com.possible_triangle.atmosphere.data;

import com.possible_triangle.atmosphere.api.v1.AtmosphereConstants;
import com.possible_triangle.atmosphere.command.LocalCommand;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangGen extends LanguageProvider {

    public LangGen(PackOutput output) {
        super(output, AtmosphereConstants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(LocalCommand.LIST_HEADER, "there are %s local weather providers");
        add(LocalCommand.LIST_ENTRY, "%s");
    }

}
