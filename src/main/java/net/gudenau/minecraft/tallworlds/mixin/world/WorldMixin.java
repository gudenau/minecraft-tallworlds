package net.gudenau.minecraft.tallworlds.mixin.world;

import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change height from 256 blocks to 512
 * */
@Mixin(World.class)
public abstract class WorldMixin implements IWorld, AutoCloseable{
    @ModifyConstant(
        method = "isHeightInvalid(I)Z",
        constant = @Constant(intValue = 256)
    )
    private static int isHeightInvalid(int original){
        return 512;
    }
}
