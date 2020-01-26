package net.gudenau.minecraft.tallworlds.mixin.world.gen.carver;

import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change cave height limit to 512 from 256
 * */
@Mixin(Carver.class)
public abstract class CarverMixin<C extends CarverConfig>{
    @ModifyConstant(
        method = "<clinit>",
        constant = @Constant(intValue = 256)
    )
    private static int staticInit(int original){
        return 512;
    }
}
