package com.possible_triangle.atmosphere.command;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.possible_triangle.atmosphere.api.v1.WeatherAPI;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceLocation;

public class LocalProviderArgument {

    public static RequiredArgumentBuilder<CommandSourceStack, ResourceLocation> existing(String name) {
        return Commands.argument(name, ResourceLocationArgument.id())
            .suggests(LocalProviderArgument::suggestions);
    }

    private static CompletableFuture<Suggestions> suggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        var weather = WeatherAPI.INSTANCE.getWeather(context.getSource().getLevel());
        return SharedSuggestionProvider.suggest(
            weather.listLocal().map(it -> it.id().toString()),
            builder
        );
    }

}
