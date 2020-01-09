package net.gudenau.minecraft.tallworlds.mixin.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.gudenau.minecraft.tallworlds.fixes.network.ClientPlayPacketListenerFix;
import net.gudenau.minecraft.tallworlds.fixes.network.LargeChunkDeltaUpdateS2CPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.ChunkDeltaUpdateS2CPacket;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ClientPlayPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Changes:
 *  - Throw an exception on old onChunkDeltaUpdate
 *  - Add new onChunkDeltaUpdate
 * */
@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements ClientPlayPacketListener, ClientPlayPacketListenerFix{
    @Shadow private MinecraftClient client;
    @Shadow private ClientWorld world;
    
    @Inject(
        method = "onChunkDeltaUpdate",
        at = @At("HEAD")
    )
    private void onChunkDeltaUpdate(ChunkDeltaUpdateS2CPacket packet, CallbackInfo callbackInfo){
        throw new RuntimeException("Use LargeChunkDeltaUpdateS2CPacket instead!");
    }
    
    @Override
    public void onChunkDeltaUpdate(LargeChunkDeltaUpdateS2CPacket packet){
        NetworkThreadUtils.forceMainThread(packet, this, client);
        LargeChunkDeltaUpdateS2CPacket.ChunkDeltaRecord[] records = packet.getRecords();
        for(LargeChunkDeltaUpdateS2CPacket.ChunkDeltaRecord chunkDeltaRecord : records){
            world.setBlockStateWithoutNeighborUpdates(
                chunkDeltaRecord.getBlockPos(),
                chunkDeltaRecord.getState()
            );
        }
    }
}
