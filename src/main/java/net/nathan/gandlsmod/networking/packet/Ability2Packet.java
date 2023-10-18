package net.nathan.gandlsmod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;

import java.util.function.Supplier;

public class Ability2Packet {





    public Ability2Packet(){
    }

    public Ability2Packet(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context= supplier.get();
        context.enqueueWork(() -> {

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel().getLevel();
            Level pLevel = player.level();

            //SERVER SIDE

            //Check if player is near water?

            player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(playerThirst -> {

                LargeFireball WS = new LargeFireball(pLevel, player, 0.0f, 0.0f, 0.0f, 5);
                WS.moveTo(player.getX(),player.getY()+1.5,player.getZ());
                WS.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                WS.setDeltaMovement(player.getLookAngle().x * 2f, player.getLookAngle().y * 2f, player.getLookAngle().z * 2f);
                pLevel.addFreshEntity(WS);


                ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                        playerThirst), (player));
            });





        });

        return true;
    }

    private boolean hasWaterAroundThem(ServerPlayer player, ServerLevel level, int size) {
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(blockState -> blockState.is(Blocks.WATER)).toArray().length > 0;
    }
}
