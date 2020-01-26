package net.gudenau.minecraft.tallworlds.mixin.world.gen.chunk;

import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Make getMinY return 512 instead of 256
 * */
@Mixin(ChunkGeneratorConfig.class)
public abstract class ChunkGeneratorConfigMixin{
    @ModifyConstant(
        method = "getMinY",
        constant = @Constant(intValue = 256)
    )
    public int getMinY(int original){
        return 512;
    }
}
