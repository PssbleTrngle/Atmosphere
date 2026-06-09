package com.possible_triangle.atmosphere.api.v1;

import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

class Heartbeats {

    record BlockEntityTypeCheck(
        Holder<BlockEntityType<?>> type,
        BlockPos pos
    ) implements ProviderHeartbeat {
        @Override
        public boolean validate(ServerLevel level) {
            var blockEntity = level.getBlockEntity(pos);
            if (blockEntity == null) return false;
            return type.value() == blockEntity.getType();
        }
    }

    record BlockEntityClassCheck<T extends BlockEntity>(
        Class<T> clazz,
        BlockPos pos,
        @Nullable Predicate<T> test
    ) implements ProviderHeartbeat {
        @Override
        public boolean validate(ServerLevel level) {
            var blockEntity = level.getBlockEntity(pos);
            if (!clazz.isInstance(blockEntity)) return false;
            if (test == null) return true;
            //noinspection unchecked
            return test.test((T) blockEntity);
        }
    }

    record EntityCheck(
        UUID id
    ) implements ProviderHeartbeat {
        @Override
        public boolean validate(ServerLevel level) {
            var entity = level.getEntity(id);
            return entity != null;
        }
    }

}
