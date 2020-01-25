package net.gudenau.minecraft.tallworlds.mixin.client.network.packet;

import net.minecraft.client.network.packet.ChunkDataS2CPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change modification bitmask to 0xFFFF_FFFF from 0x0000_FFFF
 * */
@Mixin(ChunkDataS2CPacket.class)
public abstract class ChunkDataS2CPacketMixin implements Packet<ClientPlayPacketListener>{
    @ModifyConstant(
        method = "<init>(Lnet/minecraft/world/chunk/WorldChunk;I)V",
        constant = @Constant(intValue = 0x0000_FFFF)
    )
    private static int init(int original){
        return 0xFFFF_FFFF;
    }
}
