package net.gudenau.minecraft.tallworlds.mixin.world;

import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockView.class)
public interface BlockViewMixin{
    /**
     * @author gudenau
     * @reason @ModifyConstant does not work on interfaces
     */
    @Overwrite
    default int getHeight(){
        return 512;
    }
}
