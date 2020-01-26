package net.gudenau.minecraft.tallworlds.mixin.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Changes:
 *  - Read and write BlockPos with ints instead of a long
 * */
@Mixin(PacketByteBuf.class)
public abstract class PacketByteBufMixin extends ByteBuf{
    @Shadow public abstract int readVarInt();
    @Shadow public abstract PacketByteBuf writeVarInt(int value);
    
    /**
     * @author gudenau
     * @reason Kill the longs!
     */
    @Overwrite
    public BlockPos readBlockPos() {
        return new BlockPos(
            readVarInt(),
            readVarInt(),
            readVarInt()
        );
    }
    
    /**
     * @author gudenau
     * @reason Kill the longs!
     */
    @Overwrite
    public PacketByteBuf writeBlockPos(BlockPos blockPos) {
        writeVarInt(blockPos.getX());
        writeVarInt(blockPos.getY());
        writeVarInt(blockPos.getZ());
        return (PacketByteBuf)(Object)this;
    }
}
