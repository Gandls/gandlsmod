package net.nathan.gandlsmod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.nathan.gandlsmod.effects.ModEffectInstance;
import net.nathan.gandlsmod.effects.ModEffects;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.sound.ModSounds;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;

import java.util.List;
import java.util.function.Supplier;

public class Ability3Packet {
    public Ability3Packet(){
    }

    public Ability3Packet(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context= supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel().getLevel();
            Level pLevel = player.level();

            player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(playerThirst -> {

                if(playerThirst.getpIndex() == 1){
                    //Warrior using Beserk
                    if(playerThirst.getCooldown((byte) 2) == 0) {
                        player.addEffect(new MobEffectInstance(ModEffects.BESERK.get(), 800, 0));
                        playerThirst.setCooldown(180, (byte) 2);
                        pLevel.playSeededSound(null,player.getX(),player.getY(),player.getZ(),
                                ModSounds.BERSERK_SOUND.get(), SoundSource.AMBIENT,1f,1f,0);
                    }
                }
                if(playerThirst.getpIndex() == 3){
                    if(playerThirst.getCooldown((byte) 2) == 0){
                        player.addEffect(new MobEffectInstance(ModEffects.LIMITLESS.get(),800,0));
                        pLevel.playSeededSound(null,player.getX(),player.getY(),player.getZ(),
                                ModSounds.LIMITLESS_SOUND.get(), SoundSource.AMBIENT,1f,1f,0);
                        playerThirst.setCooldown(180,(byte)2);
                    }
                }

                if(playerThirst.getpIndex() == 4){
                    if(playerThirst.getCooldown((byte) 2) == 0) {
                        //Brawler adding disarm effect to themselves
                        player.addEffect(new MobEffectInstance(ModEffects.DISARM.get(), 200, 0));
                        pLevel.playSeededSound(null,player.getX(),player.getY(),player.getZ(),
                                ModSounds.DISARM_SOUND.get(), SoundSource.AMBIENT,1f,1f,0);
                        playerThirst.setCooldown(30,(byte)2);
                    }
                }
                if(playerThirst.getpIndex() == 5){
                    if(playerThirst.getCooldown((byte) 2) == 0){
                        player.addEffect(new MobEffectInstance(ModEffects.STONEWALK.get(),300,0));
                        playerThirst.setCooldown(120,(byte)2);
                    }
                }
                if(playerThirst.getpIndex() == 6){
                    if(playerThirst.getCooldown((byte) 2) == 0){
                        player.addEffect(new MobEffectInstance(ModEffects.AGONY.get(),160));
                        List<Entity> a = player.level().getEntities(player,player.getBoundingBox().inflate(8.0f));

                        for(Entity b:a){
                            if(b instanceof LivingEntity){
                                ((LivingEntity)b).addEffect(new ModEffectInstance(ModEffects.AGONY.get(),200,0));
                            }
                        }
                        playerThirst.setCooldown(120,(byte)2);
                    }
                }
                if(playerThirst.getpIndex() == 7){
                    if(playerThirst.getCooldown((byte) 3) == 0){
                        //Shaman summoning a wolf
                        Wolf w = new Wolf(EntityType.WOLF,pLevel);
                        w.setTame(true);
                        w.setOwnerUUID(player.getUUID());
                        double s  = Math.random()*4;
                        s += 0.5;
                        //I believe this casting is unnecessary since s is a double and therefore pass by value not reference
                        //But I'm too scared to remove it
                        s = Math.round(s);


                        switch ((int)s){
                            case 1:
                                w.addEffect(new MobEffectInstance(ModEffects.PINGHEALTH.get(),400,0));
                                break;


                            case 2:
                                w.addEffect(new MobEffectInstance(ModEffects.PINGDAMAGE.get(),400,0));
                                break;


                            case 3:
                                w.addEffect(new MobEffectInstance(ModEffects.PINGRESIST.get(),400,0));
                                break;


                            case 4:
                                w.addEffect(new MobEffectInstance(ModEffects.PINGSPEED.get(),400,0));
                                break;
                        }

                        pLevel.addFreshEntity(w);
                        playerThirst.setCooldown(60,(byte) 3);
                    }
                }



                ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                        playerThirst), (player));
            });


            });
        return true;
        }
    }
