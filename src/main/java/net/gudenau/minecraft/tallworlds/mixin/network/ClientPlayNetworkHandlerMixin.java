package net.gudenau.minecraft.tallworlds.mixin.network;

import java.util.Iterator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.gudenau.minecraft.tallworlds.fixes.network.ClientPlayPacketListenerFix;
import net.gudenau.minecraft.tallworlds.fixes.network.LargeChunkDeltaUpdateS2CPacket;
import net.gudenau.minecraft.tallworlds.fixes.network.LargeLightUpdateS2CPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.ChunkDeltaUpdateS2CPacket;
import net.minecraft.client.network.packet.LightUpdateS2CPacket;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.light.LightingProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
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
    
    @Shadow @Final private RecipeManager recipeManager;
    
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
    
    /**
     * @author gudenau
     * @reason
     */
    @Overwrite
    public void onLightUpdate(LightUpdateS2CPacket packet){
        if(packet instanceof LargeLightUpdateS2CPacket){
            onLightUpdate((LargeLightUpdateS2CPacket)packet);
        }else{
            throw new RuntimeException("Use LargeLightUpdateS2CPacket instead!");
        }
    }
    
    @Override
    public void onLightUpdate(LargeLightUpdateS2CPacket packet){
        NetworkThreadUtils.forceMainThread(packet, this, client);
        int chunkX = packet.getChunkX();
        int chunkZ = packet.getChunkZ();
        LightingProvider lightingProvider = world.getChunkManager().getLightingProvider();
        long k = packet.getLongSkyLightMask();
        long l = packet.getLongFilledSkyLightMask();
        Iterator<byte[]> iterator = packet.getSkyLightUpdates().iterator();
        updateLighting(chunkX, chunkZ, lightingProvider, LightType.SKY, k, l, iterator);
        long m = packet.getLongBlockLightMask();
        long n = packet.getLongFilledBlockLightMask();
        Iterator<byte[]> iterator2 = packet.getBlockLightUpdates().iterator();
        updateLighting(chunkX, chunkZ, lightingProvider, LightType.BLOCK, m, n, iterator2);
    }
    
    private void updateLighting(int chunkX, int chunkZ, LightingProvider lightingProvider, LightType lightType, long k, long l, Iterator<byte[]> iterator) {
        for(int i = 0; i < 33; ++i) {
            int section = -1 + i;
            boolean bl = (k & 1L << i) != 0;
            boolean bl2 = (l & 1L << i) != 0;
            if(bl || bl2){
                lightingProvider.queueData(lightType, ChunkSectionPos.from(chunkX, section, chunkZ), bl ? new ChunkNibbleArray(iterator.next().clone()) : new ChunkNibbleArray());
                world.scheduleBlockRenders(chunkX, section, chunkZ);
            }
        }
        
    }
}
