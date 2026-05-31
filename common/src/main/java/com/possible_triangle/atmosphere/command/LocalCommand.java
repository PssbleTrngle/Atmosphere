package com.possible_triangle.atmosphere.command;

import static com.possible_triangle.atmosphere.command.AtmosphereCommand.translationKey;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.possible_triangle.atmosphere.api.v1.WeatherAPI;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class LocalCommand {

    public static final String LIST_HEADER = translationKey("local_providers.list.header");
    public static final String LIST_ENTRY = translationKey("local_providers.list.entry");

    public static ArgumentBuilder<CommandSourceStack, ?> build() {
        return Commands.literal("local").then(
            Commands.literal("list").executes(LocalCommand::list)
        );
    }

    private static int list(CommandContext<CommandSourceStack> context) {
        var source = context.getSource();
        var weather = WeatherAPI.INSTANCE.getWeather(source.getLevel());
        var entries = weather.listLocal().toList();

        source.sendSuccess(() -> Component.translatable(LIST_HEADER, entries.size()), true);
        entries.forEach(it -> {
            source.sendSuccess(() -> Component.translatable(LIST_ENTRY, it.id()), false);
        });

        return entries.size();
    }

}
