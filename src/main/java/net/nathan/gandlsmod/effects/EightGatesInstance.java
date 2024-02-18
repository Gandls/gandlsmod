package net.nathan.gandlsmod.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.Optional;

public class EightGatesInstance extends MobEffectInstance {
    int gate;
    public EightGatesInstance(MobEffect pEffect) {
        this(pEffect, 0, 0);
    }

    public EightGatesInstance(MobEffect pEffect, int pDuration) {
        this(pEffect, pDuration, 0);
    }

    public EightGatesInstance(MobEffect pEffect, int pDuration, int pAmplifier) {
        this(pEffect, pDuration, pAmplifier, false, true);
    }

    public EightGatesInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible) {
        this(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pVisible);
    }

    public EightGatesInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon) {
        this(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pShowIcon, (MobEffectInstance)null, pEffect.createFactorData());
    }

    public EightGatesInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon, @Nullable MobEffectInstance pHiddenEffect, Optional<FactorData> pFactorData) {
        super(pEffect,pDuration,pAmplifier,pAmbient,pVisible,pShowIcon);
        gate = getAmplifier();
    }
    public void openGate(){
        if(gate<8){
            gate++;
            this.update(new EightGatesInstance(ModEffects.EIGHTGATES.get(),this.getDuration() + 40,gate));
        }
    }

    @Override
    public void applyEffect(LivingEntity pEntity) {
        if (this.hasRemainingDuration()) {
            this.getEffect().applyEffectTick(pEntity, gate);
        }
    }

    private boolean hasRemainingDuration() {
        return this.isInfiniteDuration() || this.getDuration() > 0;
    }

}
