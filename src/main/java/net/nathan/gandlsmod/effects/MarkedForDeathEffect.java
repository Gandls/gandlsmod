package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.nathan.gandlsmod.particle.ModParticles;

public class MarkedForDeathEffect extends MobEffect {

    public MarkedForDeathEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        //Client side effects
        if (pLivingEntity.level().isClientSide()) {

            if(Math.random() < 0.4) {
                Vec3 lA = pLivingEntity.getLookAngle();
                //With each tick of the effect, do X
                pLivingEntity.level().addParticle(ModParticles.DEATH_PARTICLES.get(),
                        pLivingEntity.position().x + lA.x,
                        pLivingEntity.position().y + pLivingEntity.getEyeHeight() +lA.y,
                        pLivingEntity.position().z+ lA.z,
                        0,
                        0,
                        0);
            }




        } else {
            //Server Side Effects
        }
    }


    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
