package net.gudenau.minecraft.tallworlds.mixin.chunk;

import net.minecraft.world.BlockView;
import net.minecraft.world.StructureHolder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Changes:
 *  - Change hard coded Y check
 * */
@Mixin(Chunk.class)
public interface ChunkMixin extends BlockView, StructureHolder{
    
    @Shadow ChunkSection[] getSectionArray();
    
    /**
     * @author gudenau
     * @reason interface's don't work with @ModifyConstant
     */
    @Overwrite
    default boolean method_12228(int i, int j) {
        if (i < 0) {
            i = 0;
        }
    
        if (j >= 512) {
            j = 511;
        }
        
        j = j >> 4;
        for(int k = i >> 4; k <= j; k++) {
            if (!ChunkSection.isEmpty(getSectionArray()[k])) {
                return false;
            }
        }
    
        return true;
    }
}
