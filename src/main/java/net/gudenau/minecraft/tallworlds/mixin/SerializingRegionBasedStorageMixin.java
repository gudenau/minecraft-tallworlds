package net.gudenau.minecraft.tallworlds.mixin;

import net.minecraft.world.storage.SerializingRegionBasedStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change height from 16 chunks to 32
 * */
@Mixin(SerializingRegionBasedStorage.class)
public abstract class SerializingRegionBasedStorageMixin implements AutoCloseable{
    @ModifyConstant(
        method = "method_20368",
        constant = @Constant(intValue = 16)
    )
    private static int method_20368(int original){
        return 32;
    }
}
