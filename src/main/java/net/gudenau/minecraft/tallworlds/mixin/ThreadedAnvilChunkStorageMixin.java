package net.gudenau.minecraft.tallworlds.mixin;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.world.storage.VersionedChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change modification bitmask from 0x0000_FFFF to 0xFFFF_FFFF
 * */
@Mixin(ThreadedAnvilChunkStorage.class)
public abstract class ThreadedAnvilChunkStorageMixin extends VersionedChunkStorage implements ChunkHolder.PlayersWatchingChunkProvider{
    private ThreadedAnvilChunkStorageMixin(File file, DataFixer dataFixer){
        super(file, dataFixer);
    }
    
    @ModifyConstant(
        method = "sendChunkDataPackets",
        constant = @Constant(intValue = 0x0000_FFFF)
    )
    private static int sendChunkDataPackets(int original){
        return 0xFFFF_FFFF;
    }
}
