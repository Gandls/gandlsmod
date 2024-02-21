package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class GetOutEffect extends MobEffect {
    public GetOutEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){

        //Client side effects
        if(pLivingEntity.level().isClientSide()){
            //With each tick of the effect, do X
        }else{
            //Server Side Effects
            //1: Paralyze the entity within limitless
            switch (pAmplifier){
                case 0:
                    pLivingEntity.teleportTo(pLivingEntity.getX(),pLivingEntity.getY(),pLivingEntity.getZ());
                    pLivingEntity.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.ZERO);
                    break;
                case 1:
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,4,5));
                    break;
            }
        }

    }
    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        //When the effect ends, kick the entity out of the dimension and send them back where they came from
        //The effect instance could hold the position/world of each entity with this effect on
        //However the removeAttributeModifiers is handled by the LivingEntity not the EffectInstance

        //the solution is to hijack the event that happens when mobEffects expire
        //which occurs right before the removal of the  Instance on the entity
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
