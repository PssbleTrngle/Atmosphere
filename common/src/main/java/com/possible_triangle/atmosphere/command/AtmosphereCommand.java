package com.possible_triangle.atmosphere.command;

import com.mojang.brigadier.CommandDispatcher;
import com.possible_triangle.atmosphere.api.v1.AtmosphereConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class AtmosphereCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal(AtmosphereConstants.MOD_ID)
                .then(LocalCommand.build())
        );
    }

    public static String translationKey(String path) {
        return AtmosphereConstants.MOD_ID + ".command." + path;
    }

}
