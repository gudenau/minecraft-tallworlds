package net.gudenau.minecraft.tallworlds.mixin.server.world;

import net.minecraft.server.world.ServerTickScheduler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change height from 256 blocks to 512
 * */
@Mixin(ServerTickScheduler.class)
public abstract class ServerTickSchedulerMixin{
    @ModifyConstant(
        method = "getScheduledTicksInChunk",
        constant = @Constant(intValue = 256)
    )
    private static int getScheduledTicksInChunk(int original){
        return 512;
    }
}
