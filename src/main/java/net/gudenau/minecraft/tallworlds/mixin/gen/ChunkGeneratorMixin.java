package net.gudenau.minecraft.tallworlds.mixin.gen;

import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin<C extends ChunkGeneratorConfig>{
    @ModifyConstant(
        method = "getMaxY",
        constant = @Constant(intValue = 256)
    )
    private static int getMaxY(int original){
        return 512;
    }
}