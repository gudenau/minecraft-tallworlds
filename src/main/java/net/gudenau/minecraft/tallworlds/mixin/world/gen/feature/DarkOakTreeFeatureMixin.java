package net.gudenau.minecraft.tallworlds.mixin.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import java.util.function.Function;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BranchedTreeFeature;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.DarkOakTreeFeature;
import net.minecraft.world.gen.feature.MegaTreeFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Effectivly nop out height checks
 * */
@Mixin(DarkOakTreeFeature.class)
public abstract class DarkOakTreeFeatureMixin extends AbstractTreeFeature<MegaTreeFeatureConfig>{
    private DarkOakTreeFeatureMixin(Function<Dynamic<?>, ? extends MegaTreeFeatureConfig> configFactory){
        super(configFactory);
    }
    
    @ModifyConstant(
        method = "generate",
        constant = @Constant(intValue = 256)
    )
    private static int generate(int original){
        return Integer.MAX_VALUE;
    }
}
