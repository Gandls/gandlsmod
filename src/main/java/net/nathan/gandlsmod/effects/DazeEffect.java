package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DazeEffect extends MobEffect {

    public DazeEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){

        //Client side effects
        if(pLivingEntity.level().isClientSide()){
            //With each tick of the effect, do X
            if(pLivingEntity instanceof Player){
                Minecraft.getInstance().options.sensitivity().set(0.50 - 0.15*pAmplifier);
            }
        }else{
            //Server Side Effects
            //Particles (Halo of stars?)
            if(Math.random() < 0.1f) {
                for (int i = 0; i < 16; i++) {
                    ((ServerLevel) pLivingEntity.level()).sendParticles(ParticleTypes.WAX_OFF, pLivingEntity.getX() + Math.sin(0.125 * Math.PI * i), pLivingEntity.getY() + 2.2f, pLivingEntity.getZ() + Math.cos(0.125 * Math.PI * i),
                            2, 0, 0, 0, 0.0);
                }
            }
        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
