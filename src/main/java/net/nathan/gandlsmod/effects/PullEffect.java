package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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

public class PullEffect extends MobEffect {
    public PullEffect(MobEffectCategory mEC, int color){
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
                            -rot.x * 0.5f + pLivingEntity.position().x,
                            -rot.y * 0.5f + pLivingEntity.position().y + pLivingEntity.getEyeHeight(),
                            -rot.z * 0.5f + pLivingEntity.position().z,
                            0,
                            rot.x*20f,
                            rot.y*20f,
                            rot.z*20f,
                            0.3);
                }
            }
            List<Entity> a = pLivingEntity.level().getEntities(pLivingEntity,pLivingEntity.getBoundingBox().inflate(15));
            for(Entity b:a){
                b.setDeltaMovement(
                        b.getDeltaMovement()
                                .add(pLivingEntity.position()
                                        .subtract(b.getPosition(0))
                                .normalize()
                                .scale(0.3f)));
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


            //Make a ring
            //It's just a halo with some spunk right?
            //Each point is 5f away
            //It needs full amplitude in at least 2 dimensions to be a proper ring
            //But the wobble will be in the 3rd?

            //Write this out
            //At y=0
            //X and Z have full amplitude for a sphere of lets just say radius=1m
            //At y = root2/2
            //X has lost its amplitude by 1/4 + 1/4, it now varies by 0.5m
            //But Z varies the same amount

            //Alright found something online
            //It needs to follow these two parameters
            //The first is that it lies in sphere of radius r:
            //X^2 + Y^2 +Z^2 = r^2
            //The second is that it lies in a plane defined by a non-zero vector (a,b,c)
            //a(x) + b(y) + c(z) = 0
            //This will result in points that inevitably lie in both the sphere and the plane which, by cross-section, creates  a circle
            //The vector can be randomized for x,y,z then normalized, this will be our (a,b,c)
            //Okay wait new plan keep the (a,b,c)
            //But then we take (0,0,1) and (0,1,0), two lines we ALREADY KNOW are perpendicular to eachother
            //Two perpendicular lines that undergo the same translation will remain perpendicular
            //So we find the difference between (0,0,1) and (a,b,c), then apply that to both (0,0,1) and (0,1,0)
            //Normalize both, then boom we have a line that lies in our plane, just rotate that about the (a,b,c) like an axis
            //And you have a ring, right?
            //EDIT: nope, its not about translation its about rotation
            //If two perpendicular vectors ROTATE about the same axis they will remain perpendicular
            //So new new plan: Cross the vectors (a,b,c) and (0,0,1), this will give a new axis : U
            //Calculate the angle between (a,b,c) and rotate BOTH (0,0,1) and (0,1,0) about U for that angle
            //Edit: truly final working plan, rotate two pre-determined perpendicular angles about (a,b,c) a random but equal amount
            //Our pre-meditated ones: (0.57735026919,0.57735026919,0.57735026919) A.K.A 1/sqrt3 for x,y,z
            //And -0.221,-0.0911,0.31211 normalized


        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
