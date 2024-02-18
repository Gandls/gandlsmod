package net.nathan.gandlsmod.effects;

import com.mojang.logging.LogUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;

public class ModEffectInstance extends MobEffectInstance {
    int ramp;
    public ModEffectInstance(MobEffect pEffect) {
        this(pEffect, 0, 0);
    }

    public ModEffectInstance(MobEffect pEffect, int pDuration) {
        this(pEffect, pDuration, 0);
    }

    public ModEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier) {
        this(pEffect, pDuration, pAmplifier, false, true);
    }

    public ModEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible) {
        this(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pVisible);
    }

    public ModEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon) {
        this(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pShowIcon, (MobEffectInstance)null, pEffect.createFactorData());
    }

    public ModEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon, @Nullable MobEffectInstance pHiddenEffect, Optional<FactorData> pFactorData) {
        super(pEffect,pDuration,pAmplifier,pAmbient,pVisible,pShowIcon);

    }

    private boolean hasRemainingDuration() {
        return this.isInfiniteDuration() || this.getDuration() > 0;
    }
    @Override
    public void applyEffect(LivingEntity pEntity) {
        if (this.hasRemainingDuration()) {
            ramp++;
            this.getEffect().applyEffectTick(pEntity, this.getAmplifier() + ramp);
        }
    }
}
