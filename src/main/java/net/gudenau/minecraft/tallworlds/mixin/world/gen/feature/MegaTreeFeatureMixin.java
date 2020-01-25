package net.gudenau.minecraft.tallworlds.mixin.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import java.util.function.Function;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.MegaTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MegaTreeFeature.class)
public abstract class MegaTreeFeatureMixin<T extends TreeFeatureConfig> extends AbstractTreeFeature<T>{
    private MegaTreeFeatureMixin(Function<Dynamic<?>, ? extends T> configFactory){
        super(configFactory);
    }
    
    @ModifyConstant(
        method = "doesTreeFit",
        constant = @Constant(intValue = 256),
        expect = 2
    )
    private static int doesTreeFit(int original){
        return 512;
    }
}
