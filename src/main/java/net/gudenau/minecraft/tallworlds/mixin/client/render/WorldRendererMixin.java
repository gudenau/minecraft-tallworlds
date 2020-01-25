package net.gudenau.minecraft.tallworlds.mixin.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.resource.SynchronousResourceReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change height from 256 blocks to 512
 * */
@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin implements AutoCloseable, SynchronousResourceReloadListener{
    @ModifyConstant(
        method = "getAdjacentChunk",
        constant = @Constant(intValue = 256)
    )
    private static int getAdjacentChunk(int original){
        return 512;
    }
    
    @ModifyConstant(
        method = "renderWorldBorder",
        constant = @Constant(intValue = 256),
        expect = 8
    )
    private static int renderWorldBorder(int original){
        return 512;
    }
}
