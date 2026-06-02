package com.possible_triangle.atmosphere.command;

import static com.possible_triangle.atmosphere.command.AtmosphereCommand.translationKey;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.possible_triangle.atmosphere.api.v1.ConstantWeatherProvider;
import com.possible_triangle.atmosphere.api.v1.WeatherAPI;
import com.possible_triangle.atmosphere.api.v1.area.Area;
import com.possible_triangle.atmosphere.api.v1.area.Box;
import com.possible_triangle.atmosphere.network.AtmosphereNetwork;
import com.possible_triangle.atmosphere.network.message.RenderLocalWeatherProviders;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public class LocalCommand {

    public static final String LIST_HEADER = translationKey("local_providers.list.header");
    public static final String LIST_ENTRY = translationKey("local_providers.list.entry");
    public static final String CREATED = translationKey("local_providers.created");
    public static final String DUPLICATE = translationKey("local_providers.duplicate");
    public static final String REMOVED = translationKey("local_providers.removed");
    public static final String UNKNOWN = translationKey("local_providers.unknown");

    public static ArgumentBuilder<CommandSourceStack, ?> build() {
        return literal("local").then(
            literal("list").executes(LocalCommand::list)
        ).then(
            literal("add").then(providerBuilderNode(LocalCommand::add))
        ).then(
            literal("replace").then(providerBuilderNode(LocalCommand::replace))
        ).then(
            literal("remove").then(LocalProviderArgument.existing("id").executes(LocalCommand::remove))
        ).then(
            literal("render").executes(LocalCommand::toggleRendering)
        );
    }

    private static RequiredArgumentBuilder<CommandSourceStack, ResourceLocation> providerBuilderNode(ProviderBuilderCommand command) {
        return argument("id", ResourceLocationArgument.id()).then(
            literal("box").then(argument("from", Vec3Argument.vec3()).then(argument("to", Vec3Argument.vec3()).then(
                argument("weather", WeatherConditionArgument.create()).executes(
                    context -> command.run(context, getBox(context))
                )
            )))
        );
    }

    private static int list(CommandContext<CommandSourceStack> context) {
        var source = context.getSource();
        var weather = WeatherAPI.INSTANCE.getWeather(source.getLevel());
        var entries = weather.listLocal().toList();

        source.sendSuccess(() -> Component.translatable(LIST_HEADER, entries.size()), true);
        entries.forEach(it -> {
            source.sendSuccess(() -> Component.translatable(LIST_ENTRY, it.id().toString()), false);
        });

        return entries.size();
    }

    private static Area getBox(CommandContext<CommandSourceStack> context) {
        var from = Vec3Argument.getVec3(context, "from");
        var to = Vec3Argument.getVec3(context, "to");
        var aabb = new AABB(from, to);
        return Box.from(aabb);
    }

    private static int replace(CommandContext<CommandSourceStack> context, Area area) throws CommandSyntaxException {
        return remove(context) + add(context, area);
    }

    private static int add(CommandContext<CommandSourceStack> context, Area area) throws CommandSyntaxException {
        var id = ResourceLocationArgument.getId(context, "id");
        var weatherCondition = WeatherConditionArgument.get(context, "weather");

        var source = context.getSource();
        var weather = WeatherAPI.INSTANCE.getWeather(source.getLevel());

        var success = weather.addLocal(id, new ConstantWeatherProvider(weatherCondition), area, null);

        if (success) {
            source.sendSuccess(() -> Component.translatable(CREATED, id.toString()), true);
            return 1;
        } else {
            source.sendFailure(Component.translatable(DUPLICATE, id.toString()));
            return 0;
        }
    }

    private static int remove(CommandContext<CommandSourceStack> context) {
        var id = ResourceLocationArgument.getId(context, "id");

        var source = context.getSource();
        var weather = WeatherAPI.INSTANCE.getWeather(source.getLevel());

        var success = weather.removeLocal(id);

        if (success) {
            source.sendSuccess(() -> Component.translatable(REMOVED, id.toString()), true);
            return 1;
        } else {
            // TODO exception instead
            source.sendFailure(Component.translatable(UNKNOWN, id.toString()));
            return 0;
        }
    }

    private static int toggleRendering(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrException();
        var message = new RenderLocalWeatherProviders(RenderLocalWeatherProviders.Action.TOGGLE);
        AtmosphereNetwork.RENDER_LOCAL_WEATHER.sendTo(player, message);

        return 1;
    }

    @FunctionalInterface
    private interface ProviderBuilderCommand {
        int run(CommandContext<CommandSourceStack> context, Area area) throws CommandSyntaxException;
    }

}
