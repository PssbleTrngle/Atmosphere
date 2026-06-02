package com.possible_triangle.atmosphere.command;

import static com.possible_triangle.atmosphere.command.AtmosphereCommand.translationKey;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.possible_triangle.atmosphere.api.v1.AtmosphereRegistries;
import com.possible_triangle.atmosphere.api.v1.WeatherCondition;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;

public class WeatherConditionArgument {

    public static final String INVALID_WEATHER_CONDITION = translationKey("weather_condition.unknown");

    private static final DynamicCommandExceptionType ERROR_INVALID_WEATHER_CONDITION =
        new DynamicCommandExceptionType((key) -> Component.translatableEscape(INVALID_WEATHER_CONDITION, key));

    public static ResourceKeyArgument<WeatherCondition> create() {
        return ResourceKeyArgument.key(AtmosphereRegistries.WEATHER_CONDITION);
    }

    public static ResourceKey<WeatherCondition> get(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
        return ResourceKeyArgument.getRegistryKey(context, name, AtmosphereRegistries.WEATHER_CONDITION, ERROR_INVALID_WEATHER_CONDITION);
    }

}
