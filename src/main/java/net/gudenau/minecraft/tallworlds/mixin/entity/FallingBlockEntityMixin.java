package net.gudenau.minecraft.tallworlds.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Effectivly nop out height check
 * */
@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity{
    private FallingBlockEntityMixin(EntityType<?> type, World world){
        super(type, world);
    }
    
    @ModifyConstant(
        method = "tick",
        constant = @Constant(intValue = 256)
    )
    private static int tick(int original){
        return Integer.MAX_VALUE;
    }
}
