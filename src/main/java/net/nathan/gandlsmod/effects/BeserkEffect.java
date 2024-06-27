package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class BeserkEffect extends MobEffect {
    public BeserkEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){

        //Client side effects
        if(pLivingEntity.level().isClientSide()){
            //With each tick of the effect, do X

        }else{
            //Server Side Effects
            //PARTICLES
            ((ServerLevel)pLivingEntity.level()).sendParticles(ParticleTypes.ANGRY_VILLAGER,pLivingEntity.getX(),pLivingEntity.getY()+2.2f,pLivingEntity.getZ(),
                    4, (Math.random()*2f)-1.0f,0.4,(Math.random()*2f)-1.0f,0.1);
        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
