package net.gudenau.minecraft.tallworlds.mixin.world;

import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change height from 16 chunks to 32
 * */
@Mixin(WorldChunk.class)
public abstract class WorldChunkMixin implements Chunk{
    @ModifyConstant(
        method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/biome/source/BiomeArray;Lnet/minecraft/world/chunk/UpgradeData;Lnet/minecraft/world/TickScheduler;Lnet/minecraft/world/TickScheduler;J[Lnet/minecraft/world/chunk/ChunkSection;Ljava/util/function/Consumer;)V",
        constant = @Constant(intValue = 16),
        expect = 3
    )
    private int init_sections(int original){
        return 32;
    }
}
