package net.gudenau.minecraft.tallworlds.mixin;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import net.gudenau.minecraft.tallworlds.fixes.network.LargeLightUpdateS2CPacket;
import net.minecraft.client.network.DebugRendererInfoManager;
import net.minecraft.client.network.packet.ChunkDataS2CPacket;
import net.minecraft.client.network.packet.EntityAttachS2CPacket;
import net.minecraft.client.network.packet.EntityPassengersSetS2CPacket;
import net.minecraft.client.network.packet.LightUpdateS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.network.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ServerLightingProvider;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.storage.VersionedChunkStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Changes:
 *  - Change modification bitmask from 0x0000_FFFF to 0xFFFF_FFFF
 * */
@Mixin(ThreadedAnvilChunkStorage.class)
public abstract class ThreadedAnvilChunkStorageMixin extends VersionedChunkStorage implements ChunkHolder.PlayersWatchingChunkProvider{
    //@Shadow @Final private ServerWorld world;
    //@Shadow @Final private Int2ObjectMap<ThreadedAnvilChunkStorage.EntityTracker> entityTrackers;
    //@Shadow @Final private ServerLightingProvider serverLightingProvider;
    
    private ThreadedAnvilChunkStorageMixin(File file, DataFixer dataFixer){
        super(file, dataFixer);
    }
    
    @Redirect(
        method = "sendChunkDataPackets",
        at = @At(
            value = "NEW",
            target = "net/minecraft/client/network/packet/LightUpdateS2CPacket"
        )
    )
    private LightUpdateS2CPacket sendChunkDataPackets(ChunkPos pos, LightingProvider lightingProvider){
        return new LargeLightUpdateS2CPacket(pos, lightingProvider);
    }
    
    @ModifyConstant(
        method = "sendChunkDataPackets",
        constant = @Constant(intValue = 0x0000_FFFF)
    )
    private static int sendChunkDataPackets(int original){
        return 0xFFFF_FFFF;
    }
    
    /* *
     * @author gudenau
     * @reason int -> long
     * /
    @Overwrite
    private void sendChunkDataPackets(ServerPlayerEntity player, Packet<?>[] packets, WorldChunk chunk){
        if(packets[0] == null){
            packets[0] = new ChunkDataS2CPacket(chunk, 0xFFFF_FFFF);
            packets[1] = new LargeLightUpdateS2CPacket(chunk.getPos(), serverLightingProvider);
        }
    
        player.sendInitialChunkPackets(chunk.getPos(), packets[0], packets[1]);
        DebugRendererInfoManager.method_19775(world, chunk.getPos());
        List<MobEntity> list = Lists.newArrayList();
        List<Entity> list2 = Lists.newArrayList();
    
        for(ThreadedAnvilChunkStorage.EntityTracker entityTracker : entityTrackers.values()){
            Entity entity = entityTracker.entity;
            if(entity != player && entity.chunkX == chunk.getPos().x && entity.chunkZ == chunk.getPos().z){
                entityTracker.updateCameraPosition(player);
                if(entity instanceof MobEntity && ((MobEntity)entity).getHoldingEntity() != null){
                    list.add((MobEntity)entity);
                }
            
                if(!entity.getPassengerList().isEmpty()){
                    list2.add(entity);
                }
            }
        }
    
        if (!list.isEmpty()) {
            for(MobEntity mob : list) {
                player.networkHandler.sendPacket(new EntityAttachS2CPacket(mob, mob.getHoldingEntity()));
            }
        }
    
        if (!list2.isEmpty()) {
            for(Entity entity : list2) {
                player.networkHandler.sendPacket(new EntityPassengersSetS2CPacket(entity));
            }
        }
    }
     */
}
