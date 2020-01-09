package net.gudenau.minecraft.tallworlds.mixin.chunk;

import net.minecraft.world.ChunkSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change height from 16 chunks to 32 chunks
 * */
@Mixin(ChunkSerializer.class)
public abstract class ChunkSerializerMixin{
    @ModifyConstant(
        method = "deserialize",
        constant = @Constant(intValue = 16)
    )
    private static int deserialize(int original){
        return 32;
    }
    
    @ModifyConstant(
        method = "serialize",
        constant = @Constant(intValue = 17)
    )
    private static int serialize(int original){
        return 33;
    }
}
