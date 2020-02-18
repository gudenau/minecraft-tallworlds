package net.gudenau.minecraft.tallworlds.fixes.network;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.LightUpdateS2CPacket;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.light.LightingProvider;

public class LargeLightUpdateS2CPacket extends LightUpdateS2CPacket implements Packet<ClientPlayPacketListener>{
    private int chunkX;
    private int chunkZ;
    private long skyLightMask;
    private long blockLightMask;
    private long filledSkyLightMask;
    private long filledBlockLightMask;
    private List<byte[]> skyLightUpdates;
    private List<byte[]> blockLightUpdates;
    
    public LargeLightUpdateS2CPacket(){
        super();
    }
    
    public LargeLightUpdateS2CPacket(ChunkPos chunkPos, LightingProvider lightingProvider){
        super();
        
        chunkX = chunkPos.x;
        chunkZ = chunkPos.z;
        skyLightUpdates = Lists.newArrayList();
        blockLightUpdates = Lists.newArrayList();
        
        for(int i = 0; i < 34; ++i) {
            ChunkNibbleArray chunkNibbleArray = lightingProvider.get(LightType.SKY).getLightArray(ChunkSectionPos.from(chunkPos, -1 + i));
            ChunkNibbleArray chunkNibbleArray2 = lightingProvider.get(LightType.BLOCK).getLightArray(ChunkSectionPos.from(chunkPos, -1 + i));
            if (chunkNibbleArray != null) {
                if (chunkNibbleArray.isUninitialized()) {
                    filledSkyLightMask |= 1L << i;
                } else {
                    skyLightMask |= 1L << i;
                    skyLightUpdates.add(chunkNibbleArray.asByteArray().clone());
                }
            }
            
            if (chunkNibbleArray2 != null) {
                if (chunkNibbleArray2.isUninitialized()) {
                    filledBlockLightMask |= 1L << i;
                } else {
                    blockLightMask |= 1L << i;
                    blockLightUpdates.add(chunkNibbleArray2.asByteArray().clone());
                }
            }
        }
        
    }
    
    public LargeLightUpdateS2CPacket(ChunkPos pos, LightingProvider lightProvider, long skyLightMask, long blockLightMask) {
        super();
        
        chunkX = pos.x;
        chunkZ = pos.z;
        this.skyLightMask = skyLightMask;
        this.blockLightMask = blockLightMask;
        skyLightUpdates = Lists.newArrayList();
        blockLightUpdates = Lists.newArrayList();
        
        for(int i = 0; i < 34; ++i) {
            ChunkNibbleArray chunkNibbleArray2;
            if ((skyLightMask & 1L << i) != 0) {
                chunkNibbleArray2 = lightProvider.get(LightType.SKY).getLightArray(ChunkSectionPos.from(pos, -1 + i));
                if (chunkNibbleArray2 != null && !chunkNibbleArray2.isUninitialized()) {
                    skyLightUpdates.add(chunkNibbleArray2.asByteArray().clone());
                } else {
                    skyLightMask &= ~(1L << i);
                    if (chunkNibbleArray2 != null) {
                        filledSkyLightMask |= 1L << i;
                    }
                }
            }
            
            if ((blockLightMask & 1L << i) != 0) {
                chunkNibbleArray2 = lightProvider.get(LightType.BLOCK).getLightArray(ChunkSectionPos.from(pos, -1 + i));
                if (chunkNibbleArray2 != null && !chunkNibbleArray2.isUninitialized()) {
                    blockLightUpdates.add(chunkNibbleArray2.asByteArray().clone());
                } else {
                    blockLightMask &= ~(1L << i);
                    if (chunkNibbleArray2 != null) {
                        filledBlockLightMask |= 1L << i;
                    }
                }
            }
        }
        
    }
    
    @Override
    public void read(PacketByteBuf buf) throws IOException {
        chunkX = buf.readVarInt();
        chunkZ = buf.readVarInt();
        skyLightMask = buf.readVarLong();
        blockLightMask = buf.readVarLong();
        filledSkyLightMask = buf.readVarLong();
        filledBlockLightMask = buf.readVarLong();
        skyLightUpdates = Lists.newArrayList();
        
        int j;
        for(j = 0; j < 34; ++j) {
            if ((skyLightMask & 1L << j) != 0) {
                skyLightUpdates.add(buf.readByteArray(2048));
            }
        }
        
        blockLightUpdates = Lists.newArrayList();
        
        for(j = 0; j < 34; ++j) {
            if ((blockLightMask & 1L << j) != 0) {
                blockLightUpdates.add(buf.readByteArray(2048));
            }
        }
        
    }
    
    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeVarInt(chunkX);
        buf.writeVarInt(chunkZ);
        buf.writeVarLong(skyLightMask);
        buf.writeVarLong(blockLightMask);
        buf.writeVarLong(filledSkyLightMask);
        buf.writeVarLong(filledBlockLightMask);
        Iterator<byte[]> var2 = skyLightUpdates.iterator();
        
        byte[] cs;
        while(var2.hasNext()) {
            cs = var2.next();
            buf.writeByteArray(cs);
        }
        
        var2 = blockLightUpdates.iterator();
        
        while(var2.hasNext()) {
            cs = var2.next();
            buf.writeByteArray(cs);
        }
        
    }
    
    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        ((ClientPlayPacketListenerFix)clientPlayPacketListener).onLightUpdate(this);
    }
    
    @Override
    @Environment(EnvType.CLIENT)
    public int getChunkX() {
        return chunkX;
    }
    
    @Override
    @Environment(EnvType.CLIENT)
    public int getChunkZ() {
        return chunkZ;
    }
    
    @Environment(EnvType.CLIENT)
    public long getLongSkyLightMask() {
        return skyLightMask;
    }
    
    @Environment(EnvType.CLIENT)
    public long getLongFilledSkyLightMask() {
        return filledSkyLightMask;
    }
    
    @Override
    @Environment(EnvType.CLIENT)
    public List<byte[]> getSkyLightUpdates() {
        return skyLightUpdates;
    }
    
    @Environment(EnvType.CLIENT)
    public long getLongBlockLightMask() {
        return blockLightMask;
    }
    
    @Environment(EnvType.CLIENT)
    public long getLongFilledBlockLightMask() {
        return filledBlockLightMask;
    }
    
    @Override
    @Environment(EnvType.CLIENT)
    public List<byte[]> getBlockLightUpdates() {
        return blockLightUpdates;
    }
}
