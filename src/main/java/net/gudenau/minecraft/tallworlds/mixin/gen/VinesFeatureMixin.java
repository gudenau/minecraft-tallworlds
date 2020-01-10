package net.gudenau.minecraft.tallworlds.mixin.gen;

import com.mojang.datafixers.Dynamic;
import java.util.function.Function;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.VinesFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(VinesFeature.class)
public abstract class VinesFeatureMixin extends Feature<DefaultFeatureConfig>{
    private VinesFeatureMixin(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer){
        super(configDeserializer);
    }
    
    @ModifyConstant(
        method = "generate",
        constant = @Constant(intValue = 256)
    )
    private static int generate(int original){
        return 512;
    }
}
