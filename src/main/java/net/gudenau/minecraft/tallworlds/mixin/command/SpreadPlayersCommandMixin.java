package net.gudenau.minecraft.tallworlds.mixin.command;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(targets = "net/minecraft/server/command/SpreadPlayersCommand$Pile")
public abstract class SpreadPlayersCommandMixin{
    @ModifyConstant(
        method = "getY(Lnet/minecraft/world/BlockView;)I",
        constant = @Constant(doubleValue = 256.0D)
    )
    private static double getY(double original){
        return 512.0D;
    }
    
    @ModifyConstant(
        method = "getY(Lnet/minecraft/world/BlockView;)I",
        constant = @Constant(intValue = 257)
    )
    private static int getY(int original){
        return 513;
    }
    
    @ModifyConstant(
        method = "isSafe(Lnet/minecraft/world/BlockView;)Z",
        constant = @Constant(doubleValue = 256.0D)
    )
    private static double isSafe(double original){
        return 512.0D;
    }
}
