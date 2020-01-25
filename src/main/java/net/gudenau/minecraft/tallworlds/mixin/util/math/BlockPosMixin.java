package net.gudenau.minecraft.tallworlds.mixin.util.math;

import net.minecraft.util.DynamicSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockPos.class)
public abstract class BlockPosMixin extends Vec3i implements DynamicSerializable{
    private BlockPosMixin(int x, int y, int z){
        super(x, y, z);
    }
    
    /* Disabled for now
    @Inject(
        method = "unpackLongX",
        at = @At("HEAD")
    )
    private static void unpackLongX(long value, CallbackInfoReturnable<Integer> callbackInfo){
        throw new RuntimeException("Do not use asLong/fromLong");
    }
    
    @Inject(
        method = "unpackLongY",
        at = @At("HEAD")
    )
    private static void unpackLongY(long value, CallbackInfoReturnable<Integer> callbackInfo){
        throw new RuntimeException("Do not use asLong/fromLong");
    }
    
    @Inject(
        method = "unpackLongZ",
        at = @At("HEAD")
    )
    private static void unpackLongZ(long value, CallbackInfoReturnable<Integer> callbackInfo){
        throw new RuntimeException("Do not use asLong/fromLong");
    }
    
    @Inject(
        method = "fromLong",
        at = @At("HEAD")
    )
    private static void fromLong(long value, CallbackInfoReturnable<BlockPos> callbackInfo){
        throw new RuntimeException("Do not use asLong/fromLong");
    }
    
    @Inject(
        method = "asLong(III)J",
        at = @At("HEAD")
    )
    private static void asLong(int x, int y, int z, CallbackInfoReturnable<Long> callbackInfo) {
        throw new RuntimeException("Do not use asLong/fromLong");
    }
    
    @Inject(
        method = "asLong()J",
        at = @At("HEAD")
    )
    private void asLong(CallbackInfoReturnable<Long> callbackInfo){
        throw new RuntimeException("Do not use asLong/fromLong");
    }
     */
}
