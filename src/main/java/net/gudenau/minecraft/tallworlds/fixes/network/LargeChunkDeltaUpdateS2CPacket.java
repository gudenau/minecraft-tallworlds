package net.gudenau.minecraft.tallworlds.fixes.network;

import java.io.IOException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

public class LargeChunkDeltaUpdateS2CPacket implements Packet<ClientPlayPacketListener>{
    private ChunkPos chunkPos;
    private ChunkDeltaRecord[] records;
    
    public LargeChunkDeltaUpdateS2CPacket(){}
    
    public LargeChunkDeltaUpdateS2CPacket(int count, int[] changes, WorldChunk worldChunk){
        chunkPos = worldChunk.getPos();
        records = new ChunkDeltaRecord[count];
        for(int i = 0; i < count; i++){
            records[i] = new ChunkDeltaRecord(changes[i], worldChunk);
        }
    }
    
    @Override
    public void read(PacketByteBuf buf) throws IOException{
        chunkPos = new ChunkPos(buf.readInt(), buf.readInt());
        records = new ChunkDeltaRecord[buf.readVarInt()];
        for(int i = 0; i < records.length; i++){
            records[i] = new ChunkDeltaRecord(
                buf.readVarInt(),
                Block.STATE_IDS.get(buf.readVarInt())
            );
        }
    }
    
    @Override
    public void write(PacketByteBuf buf) throws IOException{
        buf.writeInt(chunkPos.x);
        buf.writeInt(chunkPos.z);
        buf.writeVarInt(records.length);
        for(ChunkDeltaRecord record : records){
            buf.writeVarInt(record.getPosition());
            buf.writeVarInt(Block.getRawIdFromState(record.getState()));
        }
    }
    
    @Override
    public void apply(ClientPlayPacketListener listener){
        ((ClientPlayPacketListenerFix)listener).onChunkDeltaUpdate(this);
    }
    
    @Environment(EnvType.CLIENT)
    public ChunkDeltaRecord[] getRecords(){
        return records;
    }
    
    public class ChunkDeltaRecord{
        private final int pos;
        private final BlockState state;
    
        public ChunkDeltaRecord(int pos, BlockState state) {
            this.pos = pos;
            this.state = state;
        }
    
        public ChunkDeltaRecord(int pos, WorldChunk worldChunk) {
            this.pos = pos;
            this.state = worldChunk.getBlockState(this.getBlockPos());
        }
    
        public BlockPos getBlockPos() {
            return new BlockPos(
                (pos & 0x00000000F) + chunkPos.x * 16,
                (pos >> 8) & 0x00FFFFFFF,
                ((pos >> 4) & 0x00000000F) + chunkPos.z * 16
            );
        }
    
        public int getPosition(){
            return pos;
        }
    
        public BlockState getState(){
            return state;
        }
    }
}
