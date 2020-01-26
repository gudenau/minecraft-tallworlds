package net.gudenau.minecraft.tallworlds.mixin.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Allow afterSpawn to seek higher than 256 blocks
 * */
@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, CommandOutput{
    @Environment(EnvType.CLIENT)
    @ModifyConstant(
        method = "afterSpawn",
        constant = @Constant(doubleValue = 256D)
    )
    private static double afterSpawn(double original){
        return 512D;
    }
}
