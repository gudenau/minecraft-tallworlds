package net.gudenau.minecraft.tallworlds.mixin.gen;

import com.mojang.datafixers.Dynamic;
import java.util.function.Function;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FossilFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FossilFeature.class)
public abstract class FossilFeatureMixin extends Feature<DefaultFeatureConfig>{
    private FossilFeatureMixin(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer){
        super(configDeserializer);
    }
    
    @ModifyConstant(
        method = "generate",
        constant = @Constant(intValue = 256),
        expect = 2
    )
    private static int generate(int original){
        return 512;
    }
}
