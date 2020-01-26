package net.gudenau.minecraft.tallworlds.mixin.network;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.gudenau.minecraft.tallworlds.fixes.network.LargeChunkDeltaUpdateS2CPacket;
import net.gudenau.minecraft.tallworlds.fixes.network.LargeLightUpdateS2CPacket;
import net.minecraft.client.network.packet.ChunkDeltaUpdateS2CPacket;
import net.minecraft.client.network.packet.LightUpdateS2CPacket;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.NetworkState;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.network.NetworkSide.CLIENTBOUND;

/**
 * Changes:
 *  - Add in a hack to replace packets
 *  - Replace ChunkDeltaUpdateS2CPacket
 *  - Replace LightUpdateS2CPacket
 * */
@Mixin(NetworkState.class)
public abstract class NetworkStateMixin{
    @Inject(
        method = "<clinit>",
        at = @At("TAIL")
    )
    private static void init(CallbackInfo callbackInfo){
        replacePacket(
            NetworkState.PLAY,
            CLIENTBOUND,
            ChunkDeltaUpdateS2CPacket.class,
            LargeChunkDeltaUpdateS2CPacket.class, LargeChunkDeltaUpdateS2CPacket::new
        );
        replacePacket(
            NetworkState.PLAY,
            CLIENTBOUND,
            LightUpdateS2CPacket.class,
            LargeLightUpdateS2CPacket.class, LargeLightUpdateS2CPacket::new
        );
    }
    
    private static final Map<NetworkState, Object[]> stateMap = new HashMap<>();
    @SuppressWarnings("unchecked")
    private static <P extends PacketListener, O extends Packet<P>, R extends Packet<P>> void replacePacket(NetworkState state, NetworkSide side, Class<O> original, Class<R> replacement, Supplier<R> factory){
        Object[] objects = stateMap.computeIfAbsent(state, (s)->{
            Map<NetworkSide, ?> packetHandlers = sneakyGet(s, Map.class);
            Object handler = packetHandlers.get(side);
            Object2IntMap<Class<? extends Packet<?>>> packetIds = sneakyGet(handler, Object2IntMap.class);
            List<Supplier<? extends Packet<?>>> packetFactories = sneakyGet(handler, List.class);
            
            return new Object[]{
                packetIds,
                packetFactories
            };
        });
    
        Object2IntMap<Class<? extends Packet<?>>> packetIds = (Object2IntMap<Class<? extends Packet<?>>>)objects[0];
        List<Supplier<? extends Packet<?>>> packetFactories = (List<Supplier<? extends Packet<?>>>)objects[1];
        
        int id = packetIds.getOrDefault(original, -1);
        packetIds.remove(original, id);
        packetIds.put(replacement, id);
        packetFactories.set(id, factory);
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T sneakyGet(Object instance, Class<T> type){
        for(Field field : instance.getClass().getDeclaredFields()){
            if(field.getType() == type && !Modifier.isStatic(field.getModifiers())){
                try{
                    field.setAccessible(true);
                    return (T)field.get(instance);
                }catch(ReflectiveOperationException e){
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException();
    }
}
