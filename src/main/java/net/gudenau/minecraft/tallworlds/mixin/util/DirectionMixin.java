package net.gudenau.minecraft.tallworlds.mixin.util;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import javax.annotation.Nullable;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Direction.class)
public abstract class DirectionMixin implements StringIdentifiable{
    @Shadow @Final private static Long2ObjectMap<Direction> VECTOR_TO_DIRECTION;
    
    private static long vecToLong(Vec3i value){
        return vecToLong(value.getX(), value.getY(), value.getZ());
    }
    
    private static long vecToLong(int x, int y, int z){
        return (x & 0x000000FF) |
               ((y << 8) & 0x0000FF00) |
               ((z << 16) & 0x00FF0000);
    }
    
    /**
     * @author gudenau
     * @reason Kill the longs!
     */
    @Overwrite
    private static Long method_16366(Direction direction){
        return vecToLong(direction.getVector());
    }
    
    /**
     * @author gudenau
     * @reason Kill the longs!
     */
    @Overwrite
    @Nullable
    public static Direction fromVector(int x, int y, int z) {
        return VECTOR_TO_DIRECTION.get(vecToLong(x, y, z));
    }
}
