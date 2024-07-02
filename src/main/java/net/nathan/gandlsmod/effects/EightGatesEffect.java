package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.networking.packet.ThirstDataSyncSToC;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;

import java.util.List;

public class EightGatesEffect extends MobEffect {
    public EightGatesEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){

        //Client side effects
        if(pLivingEntity.level().isClientSide()){
            //With each tick of the effect, do X
        }else{
            //Server Side Effects
            //Eight gates occurs at 8 levels, 1-8, these will be represented directly

            //1 - Strength 1,
            //2 - Strength 2 + Speed 1,
            //3 - Strength 2 + Speed 1 + Regeneration,
            //4 - Strength 2 + Speed 1 + Jump Boost 1 + Regeneration,
            //5 - Strength 3 + Speed 2 + Jump Boost 1 + Resistance 1 + Night Vision + Regeneration 2,
            //6 - Strength 3 + Speed 2 + Jump Boost 1 + Resistance 2 + Dolphins Grace + Night Vision + Regeneration 2,
            //7 - Strength 4 + Speed 3 + Jump Boost 2 + Resistance 3 + Dolphins Grace + All entities within 10 blocks glow + Night Vision + Regeneration 3,
            //8 - Instant Kills, Speed 4, Jump Boost 2 + Resistance 4 + Dolphins Grace + All entities within 20 blocks glow + Night Vision + Regeneration 3
            SimpleParticleType p = null;
            switch (pAmplifier){
                case 1:
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,40,0));
                    p = ParticleTypes.COMPOSTER;
                    break;
                case 2:
                    p = ParticleTypes.BUBBLE_POP;
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,40,1));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,40,0));
                    break;
                case 3:
                    p = ParticleTypes.ASH;
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,40,1));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,40,0));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,40,0));
                    break;
                case 4:
                    p = ParticleTypes.CRIT;
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,40,1));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,40,0));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP,40,0));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,40,0));
                    break;
                case 5:
                    p = ParticleTypes.END_ROD;
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,40,2));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,40,1));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP,40,0));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,40,0));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,40,1));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,40,0));
                    break;
                case 6:
                    p = ParticleTypes.FALLING_OBSIDIAN_TEAR;
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,40,2));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,40,1));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP,40,0));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,40,1));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE,40,0));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,40,1));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,40,0));
                    break;
                case 7:
                    p = ParticleTypes.DAMAGE_INDICATOR;
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,40,3));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,40,2));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP,40,1));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,40,2));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE,40,0));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,40,2));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,40,0));
                    List<Entity> e = pLivingEntity.level().getEntities(pLivingEntity,pLivingEntity.getBoundingBox().inflate(10));
                    for(Entity b:e){
                        if(b instanceof LivingEntity){
                            ((LivingEntity) b).addEffect(new MobEffectInstance(MobEffects.GLOWING,40,0));
                        }
                    }
                    break;
                case 8:
                    p = ParticleTypes.HEART;
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,40,20));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,40,3));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP,40,1));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,40,3));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE,40,0));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,40,2));
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,40,0));
                    List<Entity> f = pLivingEntity.level().getEntities(pLivingEntity,pLivingEntity.getBoundingBox().inflate(20));
                    for(Entity b:f){
                        if(b instanceof LivingEntity){
                            ((LivingEntity) b).addEffect(new MobEffectInstance(MobEffects.GLOWING,40,0));
                        }
                    }
                    break;
            }
            if(p != null) {
                ((ServerLevel) pLivingEntity.level()).sendParticles(p,
                        pLivingEntity.getX(),
                        pLivingEntity.getY() + 1.2f,
                        pLivingEntity.getZ(),
                        10, Math.random(), Math.random(), Math.random(), 0.5);
            }

        }

    }
    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        //The fallout of the gates is dependent strongly on the amplification

        //1 - Nausea, 15 seconds
        //2 - Nausea + Weakness 1
        //3 - Nausea + Weakness 1 + Poison
        //4 - Nausea + Weakness 2 + Poison 2 + slow 1
        //5 - Nausea + Weakness 2 + Poison 2 + Slow 2 + Blindness
        //6 - Nausea + Weakness 3 + Wither 1 + Slow 2 + Blindness + Marked for Death
        //7 - Nausea + Weakness 3 + Wither 2 + Slow 2 + Blindness + Marked for Death
        //8 - Death
        switch (pAmplifier){
            case 1:
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,300,0));
                break;
            case 2:
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,300,0));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,300,0));
                break;
            case 3:
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,300,0));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,300,0));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.POISON,300,0));
                break;
            case 4:
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,300,0));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,300,1));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.POISON,300,1));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,300,0));
                break;
            case 5:
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,300,0));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,300,1));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.POISON,300,1));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,300,1));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,300,0));
                break;
            case 6:
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,300,0));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,300,2));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER,300,0));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,300,1));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,300,0));
                pLivingEntity.addEffect(new MobEffectInstance(ModEffects.MARKED.get(),300,0));
                break;
            case 7:
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,300,0));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,300,2));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER,300,1));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,300,1));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,300,0));
                pLivingEntity.addEffect(new MobEffectInstance(ModEffects.MARKED.get(),300,0));
                break;
            case 8:
                pLivingEntity.kill();
                break;
        }

        //ADD COOLDOWN!
        pLivingEntity.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
        thirst.setCooldown(180,(byte) 3);
            ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                    thirst), (ServerPlayer) pLivingEntity);
        });


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
