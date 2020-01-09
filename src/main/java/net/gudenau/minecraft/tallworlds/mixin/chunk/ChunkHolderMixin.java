package net.gudenau.minecraft.tallworlds.mixin.chunk;

import net.gudenau.minecraft.tallworlds.fixes.network.LargeChunkDeltaUpdateS2CPacket;
import net.minecraft.client.network.packet.BlockUpdateS2CPacket;
import net.minecraft.client.network.packet.ChunkDataS2CPacket;
import net.minecraft.client.network.packet.LightUpdateS2CPacket;
import net.minecraft.network.Packet;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
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
 *  - delta format changed to 0xYYYYYYXZ from 0xXZYY
 * */
@Mixin(ChunkHolder.class)
public abstract class ChunkHolderMixin{
    @Shadow @Final private LightingProvider lightingProvider;
    @Shadow @Final private ChunkPos pos;
    
    @Shadow private int blockUpdateCount;
    @Shadow private int skyLightUpdateBits;
    @Shadow private int blockLightUpdateBits;
    @Shadow private int lightSentWithBlocksBits;
    @Shadow private int sectionsNeedingUpdateMask;
    
    @Shadow protected abstract void sendPacketToPlayersWatching(Packet<?> packet, boolean onlyOnWatchDistanceEdge);
    @Shadow protected abstract void sendBlockEntityUpdatePacket(World world, BlockPos pos);
    @Shadow public abstract WorldChunk getWorldChunk();
    
    @Final private int[] realBlockUpdatePositions;
    
    @Inject(
        method = "<init>",
        at = @At("TAIL")
    )
    private void init(ChunkPos pos, int level, LightingProvider lightingProvider, ChunkHolder.LevelUpdateListener levelUpdateListener, ChunkHolder.PlayersWatchingChunkProvider playersWatchingChunkProvider, CallbackInfo callbackInfo){
        realBlockUpdatePositions = new int[64];
    }
    
    private BlockPos blockPosFromInt(int value){
        return new BlockPos(
            (value & 0x00000000F) + pos.x * 16,
            (value >> 8) & 0x00FFFFFFF,
            ((value >> 4) & 0x00000000F) + pos.z * 16
        );
    }
    
    /**
     * @author gudenau
     * @reason short -> int
     * */
    @Overwrite
    public void markForBlockUpdate(int x, int y, int z){
        WorldChunk worldChunk = getWorldChunk();
        if(worldChunk != null){
            sectionsNeedingUpdateMask |= 1 << (y >> 4);
            if(blockUpdateCount < 64){
                int position =
                    (x & 0x0000000F) |
                    ((y << 8) & 0xFFFFFF00) |
                    ((z << 4) & 0x000000F0);
            
                for(int i = 0; i < blockUpdateCount; ++i) {
                    if (realBlockUpdatePositions[i] == position) {
                        return;
                    }
                }
            
                realBlockUpdatePositions[blockUpdateCount++] = position;
            }
        }
    }
    
    /**
     * @author gudenau
     * @reason short -> int
     */
    @Overwrite
    public void flushUpdates(WorldChunk worldChunk) {
        if(blockUpdateCount != 0 || skyLightUpdateBits != 0 || blockLightUpdateBits != 0){
            World world = worldChunk.getWorld();
            if(blockUpdateCount == 64){
                lightSentWithBlocksBits = -1;
            }
        
            if(skyLightUpdateBits != 0 || blockLightUpdateBits != 0) {
                sendPacketToPlayersWatching(new LightUpdateS2CPacket(worldChunk.getPos(), lightingProvider, skyLightUpdateBits & ~lightSentWithBlocksBits, blockLightUpdateBits & ~lightSentWithBlocksBits), true);
                int n = skyLightUpdateBits & lightSentWithBlocksBits;
                int o = blockLightUpdateBits & lightSentWithBlocksBits;
                if (n != 0 || o != 0) {
                    sendPacketToPlayersWatching(new LightUpdateS2CPacket(worldChunk.getPos(), lightingProvider, n, o), false);
                }
            
                skyLightUpdateBits = 0;
                blockLightUpdateBits = 0;
                lightSentWithBlocksBits &= ~(skyLightUpdateBits & blockLightUpdateBits);
            }
        
            if (blockUpdateCount == 1) {
                BlockPos blockPos = blockPosFromInt(realBlockUpdatePositions[0]);
                sendPacketToPlayersWatching(new BlockUpdateS2CPacket(world, blockPos), false);
                if (world.getBlockState(blockPos).getBlock().hasBlockEntity()) {
                    sendBlockEntityUpdatePacket(world, blockPos);
                }
            } else if (blockUpdateCount == 64) {
                sendPacketToPlayersWatching(new ChunkDataS2CPacket(worldChunk, sectionsNeedingUpdateMask), false);
            } else if (blockUpdateCount != 0) {
                sendPacketToPlayersWatching(new LargeChunkDeltaUpdateS2CPacket(
                    blockUpdateCount,
                    realBlockUpdatePositions,
                    worldChunk
                ), false);
            
                for(int n = 0; n < blockUpdateCount; ++n) {
                    BlockPos blockPos = blockPosFromInt(realBlockUpdatePositions[n]);
                    if (world.getBlockState(blockPos).getBlock().hasBlockEntity()) {
                        sendBlockEntityUpdatePacket(world, blockPos);
                    }
                }
            }
        
            blockUpdateCount = 0;
            sectionsNeedingUpdateMask = 0;
        }
    }
}
