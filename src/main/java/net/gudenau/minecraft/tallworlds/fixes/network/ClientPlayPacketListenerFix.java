package net.gudenau.minecraft.tallworlds.fixes.network;

public interface ClientPlayPacketListenerFix{
    void onChunkDeltaUpdate(LargeChunkDeltaUpdateS2CPacket packet);
    void onLightUpdate(LargeLightUpdateS2CPacket packet);
}
