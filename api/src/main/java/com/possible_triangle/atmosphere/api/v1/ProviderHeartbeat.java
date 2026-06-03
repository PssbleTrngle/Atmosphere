package com.possible_triangle.atmosphere.api.v1;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ProviderHeartbeat {

    boolean validate(ServerLevel level);

    static <T extends BlockEntity> ProviderHeartbeat hasBlockEntity(Class<T> clazz, BlockPos pos) {
        return new BlockEntityCheck<>(clazz, pos, null);
    }

    static <T extends BlockEntity> ProviderHeartbeat hasBlockEntity(Class<T> clazz, BlockPos pos, Predicate<T> test) {
        return new BlockEntityCheck<>(clazz, pos, test);
    }

    class BlockEntityCheck<T extends BlockEntity> implements ProviderHeartbeat {
        private final Class<T> clazz;
        private final BlockPos pos;
        private final @Nullable Predicate<T> test;

        private BlockEntityCheck(Class<T> clazz, BlockPos pos, @Nullable Predicate<T> test) {
            this.clazz = clazz;
            this.pos = pos;
            this.test = test;
        }

        @Override
        public boolean validate(ServerLevel level) {
            var blockEntity = level.getBlockEntity(pos);
            if (!clazz.isInstance(blockEntity)) return false;
            if (test == null) return true;
            //noinspection unchecked
            return test.test((T) blockEntity);
        }
    }

}
