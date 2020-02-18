package net.gudenau.minecraft.tallworlds.mixin.world.biome;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Effectively nop out height checks on snow and ice
 * */
@Mixin(Biome.class)
public abstract class BiomeMixin{
    @ModifyConstant(
        method = "canSetSnow",
        constant = @Constant(intValue = 256)
    )
    private static int canSetSnow(int original){
        return Integer.MAX_VALUE;
    }
    
    @ModifyConstant(
        method = "canSetIce(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Z)Z",
        constant = @Constant(intValue = 256)
    )
    private static int canSetIce(int original){
        return Integer.MAX_VALUE;
    }
}
