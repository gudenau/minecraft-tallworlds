package net.gudenau.minecraft.tallworlds.mixin.server;

import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin{
    @ModifyConstant(
        method = "respawnPlayer",
        constant = @Constant(doubleValue = 256.0D)
    )
    private static double respawnPlayer(double original){
        return 512.0D;
    }
}
