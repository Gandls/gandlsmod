package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class LimitlessEffect extends MobEffect {

    public LimitlessEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){

        //Client side effects
        if(pLivingEntity.level().isClientSide()){
            //With each tick of the effect, do X

        }else{
            //Server Side Effects
            //Get all Entities within 20/25 blocks
            //If they are a projectile, slow them depending on how close they are
            List<Entity> e = pLivingEntity.level().getEntities(pLivingEntity,pLivingEntity.getBoundingBox().inflate(20));
            Vec3 pos = pLivingEntity.getPosition(0);
            for(Entity b:e){
                if(b instanceof Projectile){
                    //Linear relationship with the "friction", to avoid floating point issues only do the math at reasonable numbers
                    if(b.getDeltaMovement().length() > 0.1f) {
                        b.setDeltaMovement(b.getDeltaMovement().scale(((b.getPosition(0).subtract(pos).length()) / 25.0f)));
                    }else{

                    }
                }
            }

            if(Math.random() < 0.4f) {
                for (int i = 0; i < 16; i++) {
                    ((ServerLevel) pLivingEntity.level()).sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            pLivingEntity.getX() + Math.sin(0.125 * Math.PI * i)*0.5f,
                            pLivingEntity.getY() +  pLivingEntity.getEyeHeight() -0.5f,
                            pLivingEntity.getZ() + Math.cos(0.125 * Math.PI * i)*0.5f,
                            0, 0, 0, 0, 0.0);
                    ((ServerLevel) pLivingEntity.level()).sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            pLivingEntity.getX() + Math.sin(0.125 * Math.PI * i)*0.4f,
                            pLivingEntity.getY() +  pLivingEntity.getEyeHeight() -0.2f,
                            pLivingEntity.getZ() + Math.cos(0.125 * Math.PI * i)*0.4f,
                            0, 0, 0, 0, 0.0);
                    ((ServerLevel) pLivingEntity.level()).sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            pLivingEntity.getX() + Math.sin(0.125 * Math.PI * i)*0.2f,
                            pLivingEntity.getY() +  pLivingEntity.getEyeHeight() +0.2f,
                            pLivingEntity.getZ() + Math.cos(0.125 * Math.PI * i)*0.2f,
                            0, 0, 0, 0, 0.0);
                    ((ServerLevel) pLivingEntity.level()).sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            pLivingEntity.getX() + Math.sin(0.125 * Math.PI * i)*0.5f,
                            pLivingEntity.getY() +  pLivingEntity.getEyeHeight() -0.8f,
                            pLivingEntity.getZ() + Math.cos(0.125 * Math.PI * i)*0.5f,
                            0, 0, 0, 0, 0.0);
                    ((ServerLevel) pLivingEntity.level()).sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            pLivingEntity.getX() + Math.sin(0.125 * Math.PI * i)*0.5f,
                            pLivingEntity.getY() +  pLivingEntity.getEyeHeight() -1.1f,
                            pLivingEntity.getZ() + Math.cos(0.125 * Math.PI * i)*0.5f,
                            0, 0, 0, 0, 0.0);
                    ((ServerLevel) pLivingEntity.level()).sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            pLivingEntity.getX() + Math.sin(0.125 * Math.PI * i)*0.3f,
                            pLivingEntity.getY() +  pLivingEntity.getEyeHeight() -1.4f,
                            pLivingEntity.getZ() + Math.cos(0.125 * Math.PI * i)*0.3f,
                            0, 0, 0, 0, 0.0);
                }
            }
        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
