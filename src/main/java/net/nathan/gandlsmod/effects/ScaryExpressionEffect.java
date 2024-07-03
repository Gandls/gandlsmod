package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.nathan.gandlsmod.Helper;
import net.nathan.gandlsmod.particle.ModParticles;

import java.util.List;

public class ScaryExpressionEffect extends MobEffect {

    public ScaryExpressionEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){

        //Client side effects
        if(pLivingEntity.level().isClientSide()){
            //With each tick of the effect, do X

        }else{
            //Server Side Effects
            //Get a list of nearby entities
            //If an (non-darkness)entity's look angle is within 45 degrees of the difference between your positions, darkness them, deal 7 hearts of damage
            List<Entity> e = pLivingEntity.level().getEntities(pLivingEntity,pLivingEntity.getBoundingBox().inflate(20));
            DamageSource x = pLivingEntity.level().damageSources().fellOutOfWorld();
            Vec3 pos = pLivingEntity.getEyePosition();
            for(Entity b:e){
                if(b instanceof LivingEntity) {
                    if(((LivingEntity) b).getEffect(MobEffects.DARKNESS) == null) {
                        //a-b is how b gets to a
                        pos = pos.subtract(b.getEyePosition(0));
                        if (Helper.angleBetween(b.getLookAngle(), pos) < 0.707) {
                            ((LivingEntity) b).addEffect(new MobEffectInstance(MobEffects.DARKNESS, 400, 0));
                            b.hurt(x,14);
                        }
                        pos = pos.add(b.getEyePosition(0));
                    }
                }
            }

            if(pAmplifier == 1){
                pLivingEntity.level().addParticle(ModParticles.SCARY_EXPRESSION.get(), pLivingEntity.getX(), pLivingEntity.getY() + 2.5f, pLivingEntity.getZ(), 0, 0, 0);
            }

        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
