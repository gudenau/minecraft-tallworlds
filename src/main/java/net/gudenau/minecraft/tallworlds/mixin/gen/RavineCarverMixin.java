package net.gudenau.minecraft.tallworlds.mixin.gen;

import com.mojang.datafixers.Dynamic;
import java.util.function.Function;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.RavineCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RavineCarver.class)
public abstract class RavineCarverMixin extends Carver<ProbabilityConfig>{
    private RavineCarverMixin(Function<Dynamic<?>, ? extends ProbabilityConfig> configDeserializer, int heightLimit){
        super(configDeserializer, heightLimit);
    }
    
    @ModifyConstant(
        method = "<init>",
        constant = @Constant(intValue = 256)
    )
    private static int init(int originalValue){
        return 512;
    }
}
