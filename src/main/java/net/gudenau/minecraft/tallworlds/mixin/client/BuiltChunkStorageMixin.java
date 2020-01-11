package net.gudenau.minecraft.tallworlds.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BuiltChunkStorage;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Changes:
 *  - Change height from 16 chunks to 32
 * */
@Environment(EnvType.CLIENT)
@Mixin(BuiltChunkStorage.class)
public abstract class BuiltChunkStorageMixin{
    @Shadow protected int sizeX;
    
    @ModifyConstant(
        method = "setViewDistance",
        constant = @Constant(intValue =  16)
    )
    private int setViewDistance_sizeY(int original){
        return Math.min(Math.max(original, sizeX), 32);
    }
}
