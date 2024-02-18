package net.nathan.gandlsmod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.nathan.gandlsmod.effects.EightGatesEffect;
import net.nathan.gandlsmod.effects.EightGatesInstance;
import net.nathan.gandlsmod.effects.ModEffects;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;
import net.nathan.gandlsmod.worldgen.dimension.ModDimensions;

import java.util.List;
import java.util.function.Supplier;

public class UltimatePacket {

    public UltimatePacket(){
    }

    public UltimatePacket(FriendlyByteBuf buf){

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


            player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(playerThirst -> {

                if(playerThirst.getpIndex() == 1){
                    if(playerThirst.getCooldown((byte) 3) <= 0){
                        player.addEffect(new MobEffectInstance(ModEffects.EXECUTE.get(),140,0));
                        //playerThirst.setCooldown(3600,(byte) 3);
                    }
                }
                if(playerThirst.getpIndex() == 3) {
                    //Change dimension
                    player.sendSystemMessage(Component.literal("Attempting dimension change"));
                    if (player.level() instanceof ServerLevel serverlevel) {
                        player.sendSystemMessage(Component.literal("This is a server level"));
                        MinecraftServer minecraftserver = serverlevel.getServer();
                        ResourceKey<Level> resourcekey = player.level().dimension() == ModDimensions.GANDLSDIM_LEVEL_KEY ?
                                Level.OVERWORLD : ModDimensions.GANDLSDIM_LEVEL_KEY;


                        player.sendSystemMessage(Component.literal("Player is in overworld: " + (player.level().dimension() == Level.OVERWORLD)));
                        //PROBLEM: There seems to be no level associated with the resource key
                        //Solutions: Resource Key is wrong (unlikely), Level isn't ever generated (why?)
                        ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
                        if (portalDimension != null && !player.isPassenger()) {
                            player.sendSystemMessage(Component.literal("Portal dimension is Not NULL"));
                            if (resourcekey == ModDimensions.GANDLSDIM_LEVEL_KEY) {
                                player.changeDimension(portalDimension);
                            } else {
                                player.changeDimension(portalDimension);
                            }
                        }
                    }
                }

                if(playerThirst.getpIndex() == 4){
                    //Brawler using Eight Gates
                    player.sendSystemMessage(Component.literal("I register the ultimate packet and im a brawler"));
                    if(player.getEffect(ModEffects.EIGHTGATES.get()) != null){
                        player.sendSystemMessage(Component.literal("I have eight gates"));
                        //If the brawler already has eight Gates active, interpret this packet as the opening of another gate
                        ((EightGatesInstance)player.getEffect(ModEffects.EIGHTGATES.get())).openGate();
                    }else{
                        player.sendSystemMessage(Component.literal("Adding Eight Gates effect"));
                        player.addEffect(new EightGatesInstance(ModEffects.EIGHTGATES.get(),600,1));
                    }
                }

                if(playerThirst.getpIndex() == 5){
                    if(playerThirst.getCooldown((byte) 3) <= 0){
                        player.addEffect(new MobEffectInstance(ModEffects.BREATHEFIREBALL.get(),140,0));
                        player.addEffect(new MobEffectInstance(MobEffects.LEVITATION,140,0));

                        //playerThirst.setCooldown(3600,(byte) 3);
                    }
                }
                if(playerThirst.getpIndex() == 7){
                    if(playerThirst.getCooldown((byte) 3) == 0){
                        Wolf w = new Wolf(EntityType.WOLF,pLevel);
                        w.setTame(true);
                        w.setOwnerUUID(player.getUUID());
                        w.addEffect(new MobEffectInstance(ModEffects.PINGDEATHLESS.get(),300,0));
                        w.addEffect(new MobEffectInstance(MobEffects.REGENERATION,300,10));
                        pLevel.addFreshEntity(w);
                    }
                }
                if(playerThirst.getpIndex() == 8){
                    if(playerThirst.getCooldown((byte) 3) == 0){
                        player.addEffect(new MobEffectInstance(ModEffects.KNIVES.get(),200,0));
                    }
                }




                ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                        playerThirst), (player));
            });





        });

        return true;
    }
}
