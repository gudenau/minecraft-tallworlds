package net.gudenau.minecraft.tallworlds.mixin.world.gen.chunk;

import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FlatChunkGeneratorConfig.class)
public abstract class FlatChunkGeneratorConfigMixin extends ChunkGeneratorConfig{
    @ModifyConstant(
        method = "<init>",
        constant = @Constant(intValue = 256)
    )
    private static int init(int original){
        return 512;
    }
    
    @ModifyConstant(
        method = "parseLayerString",
        constant = @Constant(intValue = 256)
    )
    private static int parseLayerString(int original){
        return 512;
    }
}
