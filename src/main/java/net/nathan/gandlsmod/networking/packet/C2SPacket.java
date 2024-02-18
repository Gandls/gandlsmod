package net.nathan.gandlsmod.networking.packet;

import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SPacket {
    public C2SPacket(){

    }

    public C2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context= supplier.get();
        context.enqueueWork(() -> {
            //SERVER SIDE
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel().getLevel();

            EntityType.COW.spawn(level, (ItemStack) null,null,player.blockPosition(),
                    MobSpawnType.COMMAND,true,false);

        });
        return true;
    }
}
