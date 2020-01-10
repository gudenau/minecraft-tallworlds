package net.gudenau.minecraft.tallworlds.mixin.gen;

import com.mojang.datafixers.Dynamic;
import java.util.function.Function;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeMushroomFeature;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(HugeMushroomFeature.class)
public abstract class HugeMushroomFeatureMixin extends Feature<HugeMushroomFeatureConfig>{
    private HugeMushroomFeatureMixin(Function<Dynamic<?>, ? extends HugeMushroomFeatureConfig> configDeserializer){
        super(configDeserializer);
    }
    
    @ModifyConstant(
        method = "method_23374",
        constant = @Constant(intValue = 256)
    )
    private static int method_23374(int original){
        return 512;
    }
}