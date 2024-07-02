package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.nathan.gandlsmod.particle.ModParticles;
import net.nathan.gandlsmod.sound.ModSounds;

import java.util.List;

public class ExecutionEffect extends MobEffect {
    public ExecutionEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){

        //If amplifier is 0, play the sound, if amplifier is 1, don't

        //Client side effects
        if(pLivingEntity.level().isClientSide()){
            //With each tick of the effect, do X
            if(pAmplifier == 1) {
                List<Entity> a = pLivingEntity.level().getEntities(pLivingEntity, pLivingEntity.getBoundingBox().inflate(20));
                for (Entity b : a) {
                    if(b instanceof LivingEntity){
                        if(((LivingEntity) b).getHealth() < ((LivingEntity) b).getMaxHealth()*0.5f){
                            pLivingEntity.level().addParticle(ModParticles.DEATH_PARTICLES_LONG.get(), b.getX(), b.getY() + 2.5f, b.getZ(), 0, 0, 0);
                        }
                    }
                }
            }

        }else{
            //Server Side Effects
            if(pAmplifier == 1){
                pLivingEntity.level().playSeededSound(null,pLivingEntity.position().x,pLivingEntity.position().y,pLivingEntity.position().z, ModSounds.EXECUTE_EFFECT_SOUND.get(), SoundSource.BLOCKS,1,1,0);
            }
        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
