package net.gudenau.minecraft.tallworlds.mixin.fluid;

import net.minecraft.fluid.BaseFluid;
import net.minecraft.fluid.LavaFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LavaFluid.class)
public abstract class LavaFluidMixin extends BaseFluid{
    @ModifyConstant(
        method = "method_15817",
        constant = @Constant(intValue = 256)
    )
    private static int method_15817(int original){
        return 512;
    }
}
