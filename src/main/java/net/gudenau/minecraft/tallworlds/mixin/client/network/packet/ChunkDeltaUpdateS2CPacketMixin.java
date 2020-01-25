package net.gudenau.minecraft.tallworlds.mixin.client.network.packet;

import net.minecraft.client.network.packet.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Changes:
 *  - Throw an exception on instantiation
 * */
@Mixin(ChunkDeltaUpdateS2CPacket.class)
public abstract class ChunkDeltaUpdateS2CPacketMixin implements Packet<ClientPlayPacketListener>{
    @Inject(
        method = "<init>(I[SLnet/minecraft/world/chunk/WorldChunk;)V",
        at = @At("TAIL")
    )
    private void init(int i, short[] ss, WorldChunk worldChunk, CallbackInfo callbackInfo){
        throw new RuntimeException("Use LargeChunkDeltaUpdateS2CPacket instead!");
    }
    
    @Inject(
        method = "<init>()V",
        at = @At("TAIL")
    )
    private void init(CallbackInfo callbackInfo){
        throw new RuntimeException("Use LargeChunkDeltaUpdateS2CPacket instead!");
    }
}
