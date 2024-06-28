package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;

import java.util.List;
import java.util.Map;

public class PingEffect extends MobEffect {
    MobEffect e;
    public PingEffect(MobEffectCategory mEC, int color, MobEffect effect)
    {
        super(mEC,color);
        e = effect;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){

        //Client side effects
        if(pLivingEntity.level().isClientSide()){
            //With each tick of the effect, do X
        }else{
            //Server Side Effects
            //Get all players within x blocks (determined by amplifier), apply the effect given
            List<Entity> a = pLivingEntity.level().getEntities(pLivingEntity,pLivingEntity.getBoundingBox().inflate(5));
            for(Entity b:a){
                if(b instanceof Player){
                    b.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst ->
                    {
                        if(thirst.getpIndex() == 7){
                            ((LivingEntity) b).addEffect(new MobEffectInstance(e,10,0));
                        }
                    });
                }
            }
            if(Math.random() < 0.4f) {
                for (int i = 0; i < 32; i++) {
                    ((ServerLevel) pLivingEntity.level()).sendParticles(ParticleTypes.ELECTRIC_SPARK, pLivingEntity.getX() + Math.sin(0.0625 * Math.PI * i)*5f, pLivingEntity.getY() +  pLivingEntity.getEyeHeight(), pLivingEntity.getZ() + Math.cos(0.0625 * Math.PI * i)*5f,
                            0, 0, 0, 0, 0.0);
                }
            }
        }
    }
    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        pLivingEntity.kill();
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
