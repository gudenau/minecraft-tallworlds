package net.gudenau.minecraft.tallworlds.mixin.world;

import net.minecraft.world.BlockRenderView;
import net.minecraft.world.CollisionView;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.source.BiomeAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Changes:
 *  - Change height from 256 blocks to 512
 * */
@Mixin(WorldView.class)
public interface WorldViewMixin extends BlockRenderView, CollisionView, BiomeAccess.Storage{
    @Shadow @Deprecated boolean isChunkLoaded(int x, int y);
    
    /**
     * @author gudenau
     * @reason interface's don't work with @ModifyConstant
     */
    @Overwrite @Deprecated
    default boolean isRegionLoaded(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        if (maxY >= 0 && minY < 512) {
            minX >>= 4;
            minZ >>= 4;
            maxX >>= 4;
            maxZ >>= 4;
        
            for(int i = minX; i <= maxX; ++i) {
                for(int j = minZ; j <= maxZ; ++j) {
                    if (!isChunkLoaded(i, j)) {
                        return false;
                    }
                }
            }
        
            return true;
        } else {
            return false;
        }
    }
}
