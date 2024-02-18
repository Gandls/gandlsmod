package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AgonyEffect extends MobEffect {

    public AgonyEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){

        //Client side effects
        if(pLivingEntity.level().isClientSide()){
            //With each tick of the effect, do X
        }else{
            pLivingEntity.hurt(pLivingEntity.level().damageSources().wither(),pAmplifier);
            //Server Side Effects
            //For every 10 ticks, damage increases by one
            //So after 1 second, agony should be doing 2 hearts a tick
            //after 2, 3 hearts
        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        int j = 25 >> pAmplifier;
        if (j > 0) {
            return pDuration % j == 0;
        } else {
            return true;
        }
    }
}
