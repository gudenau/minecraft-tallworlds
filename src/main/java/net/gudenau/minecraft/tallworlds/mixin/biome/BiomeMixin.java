package net.gudenau.minecraft.tallworlds.mixin.biome;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Biome.class)
public abstract class BiomeMixin{
    @ModifyConstant(
        method = "canSetSnow(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Z)Z",
        constant = @Constant(intValue = 256)
    )
    private static int canSetSnow(int original){
        return 512;
    }
    
    @ModifyConstant(
        method = "canSetIce",
        constant = @Constant(intValue = 256)
    )
    private static int canSetIce(int original){
        return 512;
    }
}
