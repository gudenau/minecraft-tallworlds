package net.gudenau.minecraft.tallworlds.mixin.world;

import net.minecraft.util.PackedIntegerArray;
import net.minecraft.world.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Changes:
 *  - Expand bit-width to fit 512 high worlds
 * */
@Mixin(Heightmap.class)
public abstract class HeightmapMixin{
    @Redirect(
        method = "<init>",
        at = @At(
            value = "NEW"
        )
    )
    public PackedIntegerArray constructStorage(int bits, int size){
        return new PackedIntegerArray(10, 256);
    }
}
