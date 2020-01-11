package net.gudenau.minecraft.tallworlds.mixin.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PacketByteBuf.class)
public abstract class PacketByteBufMixin extends ByteBuf{
    @Shadow public abstract int readVarInt();
    @Shadow public abstract PacketByteBuf writeVarInt(int value);
    
    /* * Disabled for now.
     * @author gudenau
     * @reason Kill the longs!
     * /
    @Overwrite
    public BlockPos readBlockPos() {
        return new BlockPos(
            readVarInt(),
            readVarInt(),
            readVarInt()
        );
    }*/
    
    /* * Disabled for now.
     * @author gudenau
     * @reason Kill the longs!
     * /
    @Overwrite
    public PacketByteBuf writeBlockPos(BlockPos blockPos) {
        writeVarInt(blockPos.getX());
        writeVarInt(blockPos.getY());
        writeVarInt(blockPos.getZ());
        return (PacketByteBuf)(Object)this;
    }
     */
}
