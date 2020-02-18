package net.gudenau.minecraft.tallworlds.mixin.server.world;

import net.gudenau.minecraft.tallworlds.fixes.network.LargeChunkDeltaUpdateS2CPacket;
import net.gudenau.minecraft.tallworlds.fixes.network.LargeLightUpdateS2CPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.LightType;
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
    @Shadow private int sectionsNeedingUpdateMask;
    
    @Shadow protected abstract void sendPacketToPlayersWatching(Packet<?> packet, boolean onlyOnWatchDistanceEdge);
    @Shadow protected abstract void sendBlockEntityUpdatePacket(World world, BlockPos pos);
    @Shadow public abstract WorldChunk getWorldChunk();
    
    @Final private int[] realBlockUpdatePositions;
    
    private long realBlockLightUpdateBits;
    private long realSkyLightUpdateBits;
    private long realLightSentWithBlocksBits;
    
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
     * @reason int -> long
     */
    @Overwrite
    public void markForLightUpdate(LightType type, int y) {
        WorldChunk worldChunk = getWorldChunk();
        if (worldChunk != null) {
            worldChunk.setShouldSave(true);
            if (type == LightType.SKY) {
                realSkyLightUpdateBits |= 1L << y - -1;
            } else {
                realBlockLightUpdateBits |= 1L << y - -1;
            }
        }
    }
    
    /**
     * @author gudenau
     * @reason short -> int
     */
    @Overwrite
    public void flushUpdates(WorldChunk worldChunk) {
        if(blockUpdateCount != 0 || realSkyLightUpdateBits != 0 || realBlockLightUpdateBits != 0){
            World world = worldChunk.getWorld();
            if(blockUpdateCount == 64){
                realLightSentWithBlocksBits = -1;
            }
        
            if(realSkyLightUpdateBits != 0 || realBlockLightUpdateBits != 0) {
                sendPacketToPlayersWatching(new LargeLightUpdateS2CPacket(worldChunk.getPos(), lightingProvider, realSkyLightUpdateBits & ~realLightSentWithBlocksBits, realBlockLightUpdateBits & ~realLightSentWithBlocksBits), true);
                long n = realSkyLightUpdateBits & realLightSentWithBlocksBits;
                long o = realBlockLightUpdateBits & realLightSentWithBlocksBits;
                if (n != 0 || o != 0) {
                    sendPacketToPlayersWatching(new LargeLightUpdateS2CPacket(worldChunk.getPos(), lightingProvider, n, o), false);
                }
            
                realSkyLightUpdateBits = 0;
                realBlockLightUpdateBits = 0;
                realLightSentWithBlocksBits &= ~(realSkyLightUpdateBits & realBlockLightUpdateBits);
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
