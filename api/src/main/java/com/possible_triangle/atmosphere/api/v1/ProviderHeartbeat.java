package com.possible_triangle.atmosphere.api.v1;

import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

@FunctionalInterface
public interface ProviderHeartbeat {

    boolean validate(ServerLevel level);

    static ProviderHeartbeat hasBlockEntity(Holder<BlockEntityType<?>> type, BlockPos pos) {
        return new Heartbeats.BlockEntityTypeCheck(type, pos);
    }

    static <T extends BlockEntity> ProviderHeartbeat hasBlockEntity(Class<T> clazz, BlockPos pos) {
        return new Heartbeats.BlockEntityClassCheck<>(clazz, pos, null);
    }

    static <T extends BlockEntity> ProviderHeartbeat hasBlockEntity(Class<T> clazz, BlockPos pos, Predicate<T> test) {
        return new Heartbeats.BlockEntityClassCheck<>(clazz, pos, test);
    }

    static ProviderHeartbeat hasEntity(UUID id) {
        return new Heartbeats.EntityCheck(id);
    }

}
