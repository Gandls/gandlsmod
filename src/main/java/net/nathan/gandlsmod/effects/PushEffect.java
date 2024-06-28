package net.nathan.gandlsmod.effects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.nathan.gandlsmod.Helper;
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
            if(Math.random() < 0.1f) {
                Vec3 lA = new Vec3(Math.random(), Math.random(), Math.random()).normalize();
                //pPlayer.sendSystemMessage(Component.literal(lA.toString()));
                Vec3 t = new Vec3(0.57735, 0.57735, 0.57735).normalize();

                Vec3 z = new Vec3(-0.221, -0.0911, 0.31211).normalize();

                double r = Math.random() * 180;
                t = Helper.rotate(t, lA, r);
                z = Helper.rotate(z, lA, r);
                //Get the cross product
                Vec3 cross = lA.cross(t);
                //Problem: The cross is always y=0 since x/z are 0, meaning the cross is in the xz plane
                //if the cross is always in the x/z plane then its always 90 degrees to t
                //If its always 90 to t, then t will get rotated down to the x/z plane
                //pPlayer.sendSystemMessage(Component.literal(cross.toString()));
                for (int i = 0; i < 36; i++) {
                    Vec3 rot = Helper.rotate(z, t, i * 10).normalize();
                    ((ServerLevel) pLivingEntity.level()).sendParticles(ParticleTypes.PORTAL,
                            rot.x*5f + pLivingEntity.position().x,
                            rot.y*5f + pLivingEntity.position().y + pLivingEntity.getEyeHeight(),
                            rot.z*5f + pLivingEntity.position().z,
                            0,
                            -rot.x*5f,
                            -rot.y*5f,
                            -rot.z*5f,
                            1.5);
                }
            }
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
