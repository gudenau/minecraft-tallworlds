package net.gudenau.minecraft.tallworlds.mixin.network;

import net.gudenau.minecraft.tallworlds.fixes.network.ClientPlayPacketListenerFix;
import net.gudenau.minecraft.tallworlds.fixes.network.LargeChunkDeltaUpdateS2CPacket;
import net.gudenau.minecraft.tallworlds.fixes.network.LargeLightUpdateS2CPacket;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.PacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Changes:
 *  - Extends ClientPlayPacketListenerFix
 *  - Add onChunkDeltaUpdate(LargeChunkDeltaUpdateS2CPacket)
 * */
@Mixin(ClientPlayPacketListener.class)
public interface ClientPlayPacketListenerMixin extends PacketListener, ClientPlayPacketListenerFix{
    @Override
    void onChunkDeltaUpdate(LargeChunkDeltaUpdateS2CPacket packet);
    
    @Override
    void onLightUpdate(LargeLightUpdateS2CPacket packet);
}
