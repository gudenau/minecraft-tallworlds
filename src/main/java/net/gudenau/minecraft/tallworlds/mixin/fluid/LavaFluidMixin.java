package net.gudenau.minecraft.tallworlds.mixin.fluid;

import net.minecraft.fluid.BaseFluid;
import net.minecraft.fluid.LavaFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Effectivly nop out height check
 * */
@Mixin(LavaFluid.class)
public abstract class LavaFluidMixin extends BaseFluid{
    @ModifyConstant(
        method = "hasBurnableBlock",
        constant = @Constant(intValue = 256)
    )
    private static int hasBurnableBlock(int original){
        return Integer.MAX_VALUE;
    }
}
