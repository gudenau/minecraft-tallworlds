package net.gudenau.minecraft.tallworlds.mixin.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import java.util.function.Function;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BranchedTreeFeature;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Effectivly nop out height checks
 * */
@Mixin(BranchedTreeFeature.class)
public abstract class BranchedTreeFeatureMixin<T extends BranchedTreeFeatureConfig> extends AbstractTreeFeature<T>{
    private BranchedTreeFeatureMixin(Function<Dynamic<?>, ? extends T> configFactory){
        super(configFactory);
    }
    
    @ModifyConstant(
        method = "findPositionToGenerate",
        constant = @Constant(intValue = 256),
        expect = 3
    )
    private static int findPositionToGenerate(int original){
        return Integer.MAX_VALUE;
    }
}
