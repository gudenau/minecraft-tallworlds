package net.gudenau.minecraft.tallworlds.mixin.client.network.packet;

import java.io.IOException;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.packet.LightUpdateS2CPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.light.LightingProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Changes:
 *  - Prevent use by throwing exceptions
 * */
@Mixin(LightUpdateS2CPacket.class)
public abstract class LightUpdateS2CPacketMixin implements Packet<ClientPlayPacketListener>{
//    @Inject(
//        method = "<init>()V",
//        at = @At("TAIL")
//    )
//    public void init(CallbackInfo callbackInfo){
//        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
//    }
    
    @Inject(
        method = "<init>(Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/chunk/light/LightingProvider;)V",
        at = @At("TAIL")
    )
    public void init(ChunkPos chunkPos, LightingProvider lightingProvider, CallbackInfo callbackInfo){
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
    
    @Inject(
        method = "<init>(Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/chunk/light/LightingProvider;II)V",
        at = @At("TAIL")
    )
    private void init(ChunkPos pos, LightingProvider lightProvider, int skyLightMask, int blockLightMask, CallbackInfo callbackInfo){
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
    
    @Inject(
        method = "read",
        at = @At("HEAD")
    )
    public void read(PacketByteBuf buf, CallbackInfo callbackInfo) throws IOException{
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
    
    @Inject(
        method = "write",
        at = @At("HEAD")
    )
    public void write(PacketByteBuf buf, CallbackInfo callbackInfo) throws IOException{
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
    
    @Inject(
        method = "apply",
        at = @At("HEAD")
    )
    public void apply(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo callbackInfo){
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
    
    @Inject(
        method = "getChunkX",
        at = @At("HEAD")
    )
    @Environment(EnvType.CLIENT)
    public void getChunkX(CallbackInfoReturnable<Integer> callbackInfo){
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
    
    @Inject(
        method = "getChunkZ",
        at = @At("HEAD")
    )
    @Environment(EnvType.CLIENT)
    public void getChunkZ(CallbackInfoReturnable<Integer> callbackInfo){
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
    
    @Inject(
        method = "getSkyLightMask",
        at = @At("HEAD")
    )
    @Environment(EnvType.CLIENT)
    public void getSkyLightMask(CallbackInfoReturnable<Integer> callbackInfo){
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
    
    @Inject(
        method = "getFilledSkyLightMask",
        at = @At("HEAD")
    )
    @Environment(EnvType.CLIENT)
    public void getFilledSkyLightMask(CallbackInfoReturnable<Integer> callbackInfo){
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
    
    @Inject(
        method = "getSkyLightUpdates",
        at = @At("HEAD")
    )
    @Environment(EnvType.CLIENT)
    public void getSkyLightUpdates(CallbackInfoReturnable<List<byte[]>> callbackInfo){
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
    
    @Inject(
        method = "getBlockLightMask",
        at = @At("HEAD")
    )
    @Environment(EnvType.CLIENT)
    public void getBlockLightMask(CallbackInfoReturnable<Integer> callbackInfo){
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
    
    @Inject(
        method = "getFilledBlockLightMask",
        at = @At("HEAD")
    )
    @Environment(EnvType.CLIENT)
    public void getFilledBlockLightMask(CallbackInfoReturnable<Integer> callbackInfo){
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
    
    @Inject(
        method = "getBlockLightUpdates",
        at = @At("HEAD")
    )
    @Environment(EnvType.CLIENT)
    public void getBlockLightUpdates(CallbackInfoReturnable<List<byte[]>> callbackInfo) {
        throw new RuntimeException("Use LargeLightUpdateS2CPacket instead");
    }
}
