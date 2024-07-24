package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DragonBreathEffect extends MobEffect {
    DamageSource c;
    public DragonBreathEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){

        //Client side effects
        if(pLivingEntity.level().isClientSide()){
            //With each tick of the effect, do X

        }else{
            //TODO
            //Cone particles, each one going forward with a bit of randomness




            c = pLivingEntity.level().damageSources().dragonBreath();
            //Server Side Effects
            //Each tick, deal damage to the player, and damage entities in front
            pLivingEntity.hurt(c,3);
            Vec3 start = pLivingEntity.getPosition(0);
            Vec3 end = start;
            Vec3 forward = pLivingEntity.getForward();
            Vec3 up = pLivingEntity.getUpVector(0);
            Vec3 left = up.cross(forward);

            if(Math.random()<0.2) {
                for (int i = 1; i < 6; i++) {
                    //Successive boxes that get larger
                    ((ServerLevel) pLivingEntity.level()).sendParticles(ParticleTypes.PORTAL, start.x + (forward.scale(i)).x, start.y + (forward.scale(i)).y, start.z + (forward.scale(i)).z, 10, i, i, i, 0);
                }
            }

            start = start.add(left.scale(5.0f));
            start = start.add(up.scale(-2.5f));
            start = start.add(forward.scale(0.5f));


            end = end.add(left.scale(-5.0f));
            end = end.add(up.scale(3.5f));
            end = end.add(forward.scale(5.0f));



            AABB area = new AABB(start,end);

            List<Entity> s = pLivingEntity.level().getEntities(pLivingEntity,area);
            for(Entity b:s){
                if(b instanceof LivingEntity){
                    //This is running
                    pLivingEntity.heal(1);
                    MobEffectInstance l = ((LivingEntity) b).getEffect(ModEffects.DRAGONBURN.get());
                    if(l != null){
                        //If they DO have it, up the amp (to a max of 3)
                        int amp = l.getAmplifier();
                        if(amp < 2){
                            ((LivingEntity) b).addEffect(new MobEffectInstance(ModEffects.DRAGONBURN.get(),5,amp+1));
                        }
                    }else{
                        //If they DON'T have it, give them it
                        ((LivingEntity) b).addEffect(new MobEffectInstance(ModEffects.DRAGONBURN.get(),5,0));
                    }
                }
            }

        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
