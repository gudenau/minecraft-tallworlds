package net.gudenau.minecraft.tallworlds.mixin.server.dedicated;

import java.util.Properties;
import net.minecraft.server.dedicated.AbstractPropertiesHandler;
import net.minecraft.server.dedicated.ServerPropertiesHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Changes:
 *  - Change height from 256 blocks to 512
 * */
@Mixin(ServerPropertiesHandler.class)
public abstract class ServerPropertiesHandlerMixin extends AbstractPropertiesHandler<ServerPropertiesHandler>{
    public ServerPropertiesHandlerMixin(Properties properties){
        super(properties);
    }
    
    @ModifyConstant(
        method = "<init>",
        constant = @Constant(intValue = 256)
    )
    private static int init(int original){
        return 512;
    }
}
