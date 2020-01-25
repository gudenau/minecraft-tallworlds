package net.gudenau.minecraft.tallworlds.mixin.world.chunk;

import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change height from 16 chunks to 32
 * */
@Mixin(ProtoChunk.class)
public abstract class ProtoChunkMixin implements Chunk{
    @ModifyConstant(
        method = "<init>(Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/chunk/UpgradeData;[Lnet/minecraft/world/chunk/ChunkSection;Lnet/minecraft/world/ChunkTickScheduler;Lnet/minecraft/world/ChunkTickScheduler;)V",
        constant = @Constant(intValue = 16)
    )
    private int init_sections(int original){
        return 32;
    }
}
