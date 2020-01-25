package net.gudenau.minecraft.tallworlds.mixin.client.render.debug;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.debug.ChunkBorderDebugRenderer;
import net.minecraft.client.render.debug.DebugRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change height from 256 blocks to 512
 * */
@Environment(EnvType.CLIENT)
@Mixin(ChunkBorderDebugRenderer.class)
public abstract class ChunkBorderDebugRendererMixin implements DebugRenderer.Renderer{
    @ModifyConstant(
        method = "render",
        constant = @Constant(intValue = 256)
    )
    private static int render(int original){
        return 512;
    }
    
    @ModifyConstant(
        method = "render",
        constant = @Constant(doubleValue = 256.0)
    )
    private static double render(double original){
        return 512.0;
    }
}
