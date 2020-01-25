package net.gudenau.minecraft.tallworlds.mixin.world.gen.carver;

import com.mojang.datafixers.Dynamic;
import java.util.function.Function;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.CaveCarver;
import net.minecraft.world.gen.carver.UnderwaterCaveCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(UnderwaterCaveCarver.class)
public abstract class UnderwaterCaveCarverMixin extends CaveCarver{
    private UnderwaterCaveCarverMixin(Function<Dynamic<?>, ? extends ProbabilityConfig> configDeserializer, int heightLimit){
        super(configDeserializer, heightLimit);
    }
    
    @ModifyConstant(
        method = "<init>",
        constant = @Constant(intValue = 256)
    )
    private static int init(int original){
        return 512;
    }
}
