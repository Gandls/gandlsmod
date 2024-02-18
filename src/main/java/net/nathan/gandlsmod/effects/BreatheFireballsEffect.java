package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;

public class BreatheFireballsEffect extends MobEffect {
    public BreatheFireballsEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){

        //Client side effects
        if(pLivingEntity.level().isClientSide()){
            //With each tick of the effect, do X

        }else{
            Level pLevel = pLivingEntity.level();
            //Server Side Effects
            LargeFireball WS = new LargeFireball(pLevel, pLivingEntity, 0.0f, 0.0f, 0.0f, 5);
            WS.moveTo(pLivingEntity.getX(),pLivingEntity.getY()+1.5,pLivingEntity.getZ());
            WS.shootFromRotation(pLivingEntity, pLivingEntity.getXRot(), pLivingEntity.getYRot(), 0.0F, 1.5F, 1.0F);
            WS.setDeltaMovement(pLivingEntity.getLookAngle().x * 2f, pLivingEntity.getLookAngle().y * 2f, pLivingEntity.getLookAngle().z * 2f);
            pLevel.addFreshEntity(WS);
        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        int j = 15 >> pAmplifier;
        if (j > 0) {
            return pDuration % j == 0;
        } else {
            return true;
        }
    }
}
