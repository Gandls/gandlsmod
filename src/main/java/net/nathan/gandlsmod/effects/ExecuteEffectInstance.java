package net.nathan.gandlsmod.effects;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.nathan.gandlsmod.worldgen.portal.ModPortal;

import javax.annotation.Nullable;
import java.util.Optional;

public class ExecuteEffectInstance extends MobEffectInstance {
    int ticks;

    boolean apply;
    public ExecuteEffectInstance(MobEffect pEffect) {
        this(pEffect, 0, 0);
    }

    public ExecuteEffectInstance(MobEffect pEffect, int pDuration) {
        this(pEffect, pDuration, 0);
    }

    public ExecuteEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier) {
        this(pEffect, pDuration, pAmplifier, false, true);
        ticks=0;
    }


    public ExecuteEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible) {
        this(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pVisible);
    }

    public ExecuteEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon) {
        this(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pShowIcon, (MobEffectInstance)null, pEffect.createFactorData());
    }

    public ExecuteEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon, @Nullable MobEffectInstance pHiddenEffect, Optional<FactorData> pFactorData) {
        super(pEffect,pDuration,pAmplifier,pAmbient,pVisible,pShowIcon);
    }


    @Override
    public void applyEffect(LivingEntity pEntity) {
        if (this.hasRemainingDuration()) {
            if(ticks>40){
                this.getEffect().applyEffectTick(pEntity,1);
                ticks=0;
            }else{
                this.getEffect().applyEffectTick(pEntity,0);
            }
            ticks++;
        }
    }

    private boolean hasRemainingDuration() {
        return this.isInfiniteDuration() || this.getDuration() > 0;
    }
}
