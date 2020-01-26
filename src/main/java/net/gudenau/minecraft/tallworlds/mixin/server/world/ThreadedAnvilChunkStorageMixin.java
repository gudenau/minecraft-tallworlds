package net.gudenau.minecraft.tallworlds.mixin.server.world;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import net.gudenau.minecraft.tallworlds.fixes.network.LargeLightUpdateS2CPacket;
import net.minecraft.client.network.packet.LightUpdateS2CPacket;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.storage.VersionedChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Changes:
 *  - Change modification bitmask from 0x0000_FFFF to 0xFFFF_FFFF
 * */
@Mixin(ThreadedAnvilChunkStorage.class)
public abstract class ThreadedAnvilChunkStorageMixin extends VersionedChunkStorage implements ChunkHolder.PlayersWatchingChunkProvider{
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
}
