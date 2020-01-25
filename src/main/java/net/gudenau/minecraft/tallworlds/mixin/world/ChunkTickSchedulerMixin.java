package net.gudenau.minecraft.tallworlds.mixin.world;

import net.minecraft.world.ChunkTickScheduler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change height from 16 chunks to 32
 * */
@Mixin(ChunkTickScheduler.class)
public abstract class ChunkTickSchedulerMixin{
    @ModifyConstant(
        method = "<init>(Ljava/util/function/Predicate;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/nbt/ListTag;)V",
        constant = @Constant(intValue = 16)
    )
    private static int init(int original){
        return 32;
    }
}
