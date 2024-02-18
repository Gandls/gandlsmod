package net.nathan.gandlsmod.effects;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;

import java.util.List;

public class PushEffect extends MobEffect {
    public PushEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){

        //Client side effects
        if(pLivingEntity.level().isClientSide()){
            //With each tick of the effect, do X


        }else{
            List<Entity> a = pLivingEntity.level().getEntities(pLivingEntity,pLivingEntity.getBoundingBox().inflate(15));
            for(Entity b:a){
                b.setDeltaMovement(
                        b.getDeltaMovement()
                                .add(b.getPosition(0)
                                        .subtract(pLivingEntity.position())
                                        .normalize()
                                        .scale(0.3f)));
                //The player DOES go through this, but being on the server side, their movement is not affected
                if(b instanceof Player){
                    //THIS CODE RUNS
                    b.hurtMarked = true;
                    //hurtMarked is a variable in the ServerPlayer "Attack" method
                    //That forces the server version movement onto the client player
                    //If you alter the movement of a player server side
                    // you can use this to sync with the client (otherwise the change won't go through)

                    //Btw yes, it is intentional that a wizard can be affected by their own gravity stuff, it should be more utility than actual damage
                }

            }
        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
