package net.nathan.gandlsmod.networking.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;
import net.nathan.gandlsmod.client.ClientThirstData;
import net.nathan.gandlsmod.effects.ModEffects;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;

import java.util.List;
import java.util.function.Supplier;

public class DrinkWaterPacket {


    private static final String MESSAGE_DRINK_WATER = "message.gandlsmod.drink_water";
    private static final String MESSAGE_NO_WATER = "message.gandlsmod.no_water";




    public DrinkWaterPacket(){
    }

    public DrinkWaterPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context= supplier.get();
        context.enqueueWork(() -> {

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel().getLevel();
            Level pLevel = player.level();

            /*
            if(hasWaterAroundThem(player,level,2)){
                //Notify the player that water has been drunk
                player.sendSystemMessage(Component.translatable(MESSAGE_DRINK_WATER).withStyle(ChatFormatting.DARK_AQUA));
                //Play drinking sound
                level.playSound(null,player.getOnPos(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS,
                        0.5f, level.random.nextFloat() * 0.1f + 0.9f);

                //Increase the thirst level
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(playerThirst -> {
                    playerThirst.addThirst(1);
                    player.sendSystemMessage(Component.literal("Current thirst: " + playerThirst.getThirst()).withStyle(ChatFormatting.DARK_AQUA));
                    ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                            playerThirst), (player));
                });

            }else{
                //Notify the player no water is around
                player.sendSystemMessage(Component.translatable(MESSAGE_NO_WATER).withStyle(ChatFormatting.RED));
                // Output current thirst level
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(playerThirst -> {
                    player.sendSystemMessage(Component.literal("Current thirst: " + playerThirst.getThirst()).withStyle(ChatFormatting.DARK_AQUA));
                    ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                            playerThirst), (player));
                });
            }

             */


            //SERVER SIDE

            //Check if player is near water?

            player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(playerThirst -> {

                if(playerThirst.getpIndex() == 1){
                    if(playerThirst.getCooldown((byte) 0 ) == 0.0f) {
                        playerThirst.addCheck(0.5f);
                        playerThirst.setCooldown(5.0f, (byte) 0);
                    }


                    //When commands like this are done through packets
                    //The stuff happening is server sided but using ServerPlayer type instead of Player type
                    //Will break the command, its ok to just put null here
                    //pLevel.playSound(null,player.blockPosition(), SoundEvents.NOTE_BLOCK_CHIME.get(), SoundSource.BLOCKS, 1f, 1f);

                }
                if(playerThirst.getpIndex() == 3){
                    if(playerThirst.getCooldown((byte) 0) <= 0.0f){
                        //This is a Gravity Wizard using their "Push" projectile
                        //If the player has a "Push" object already, detonate it, add cooldown
                        Chicken s = new Chicken(EntityType.CHICKEN,pLevel);
                        s.moveTo(player.getX(),player.getY()+1.5,player.getZ());
                        s.setDeltaMovement(player.getLookAngle().x * 0.5f, player.getLookAngle().y * 0.5f, player.getLookAngle().z * 0.5f);
                        s.setNoGravity(true);
                        s.setDiscardFriction(true);
                        //s.setNoAi(true);
                        s.addEffect(new MobEffectInstance(ModEffects.PUSH.get(),200,0));
                        pLevel.addFreshEntity(s);
                        playerThirst.setCooldown(60,(byte) 0);
                    }
                }


                if(playerThirst.getpIndex() == 4){
                    //This is a Brawler using Daze Punch
                    //It should empower their next (fist) attack (use a bool) to
                    //Against Mobs: Slowness, Nausea, Mining Fatigue, Weakness
                    //Against Players: Nausea, Mining Fatigue, Low FOV, Low Sensitivity (Use an on/off bool + timer)
                    if(playerThirst.getCooldown((byte) 0) <= 0.0f) {
                        playerThirst.setEmpowered(true);
                    }
                }

                if(playerThirst.getpIndex() == 5){
                    if(playerThirst.getCooldown((byte) 0 ) == 0.0f) {
                        LargeFireball WS = new LargeFireball(pLevel, player, 0.0f, 0.0f, 0.0f, 5);
                        WS.moveTo(player.getX(),player.getY()+1.5,player.getZ());
                        WS.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                        WS.setDeltaMovement(player.getLookAngle().x * 2f, player.getLookAngle().y * 2f, player.getLookAngle().z * 2f);
                        pLevel.addFreshEntity(WS);
                        playerThirst.setCooldown(4.0f, (byte) 0);
                    }
                }
                if(playerThirst.getpIndex() == 6){
                    if(playerThirst.getCooldown((byte) 0 ) == 0.0f) {
                        WitherSkull WS = new WitherSkull(pLevel, player, 0.0f, 0.0f, 0.0f);
                        WS.moveTo(player.getX(), player.getY() + 1.5, player.getZ());
                        WS.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                        WS.setDeltaMovement(player.getLookAngle().x * 2f, player.getLookAngle().y * 2f, player.getLookAngle().z * 2f);
                        pLevel.addFreshEntity(WS);
                        playerThirst.setCooldown(8.0f, (byte) 0);
                    }
                }

                //If player is a shaman
                if(playerThirst.getpIndex() == 7){
                    //And they're ability is not on cooldown
                    if(playerThirst.getCooldown((byte) 1) <= 0.0f){
                        //Get all entities within 20 blocks
                        List<Entity> a = player.level().getEntities(player,player.getBoundingBox().inflate(20.0f));

                        for(Entity b:a){
                            //Check if they're actually within 20 blocks
                            if((b.position().subtract(player.position())).length() <= 20.0f){
                                //If they are, summon lightning on them
                                LightningBolt l = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                                l.setPos(b.position());
                                level.addFreshEntity(l);
                            }
                        }
                    }
                }

                if(playerThirst.getpIndex() == 8){
                    //This is an assassin Marking entities nearby for death
                    if(playerThirst.getCooldown((byte) 1) == 0){
                        List<Entity> a = player.level().getEntities(player,player.getBoundingBox().inflate(3.0f));
                        for(Entity b:a){
                            //Add the effect
                            ((LivingEntity) b ).addEffect(new MobEffectInstance(ModEffects.MARKED.get(),120,0));
                            }
                        }
                    playerThirst.setCooldown(30,(byte) 1);
                }


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
