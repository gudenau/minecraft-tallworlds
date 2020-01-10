package net.gudenau.minecraft.tallworlds.mixin.block;

import net.minecraft.block.Block;
import net.minecraft.block.ChorusFlowerBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ChorusFlowerBlock.class)
public abstract class ChorusFlowerBlockMixin extends Block{
    private ChorusFlowerBlockMixin(Settings settings){
        super(settings);
    }
    
    @ModifyConstant(
        method = "scheduledTick",
        constant = @Constant(intValue = 256)
    )
    private static int scheduledTick(int original){
        return 512;
    }
}
