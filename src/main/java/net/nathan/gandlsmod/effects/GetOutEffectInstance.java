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

public class GetOutEffectInstance extends MobEffectInstance {
    BlockPos p;
    ResourceKey<Level> origin;

    boolean apply;
    public GetOutEffectInstance(MobEffect pEffect) {
        this(pEffect, 0, 0);
    }

    public GetOutEffectInstance(MobEffect pEffect, int pDuration) {
        this(pEffect, pDuration, 0);
    }

    public GetOutEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier) {
        this(pEffect, pDuration, pAmplifier, false, true);
    }

    public GetOutEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, BlockPos pos, ResourceKey<Level> pLevel) {
        this(pEffect, pDuration, pAmplifier, false, true);
        p = pos;
        origin = pLevel;
        apply = true;
    }
    public GetOutEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, BlockPos pos, ResourceKey<Level> pLevel, boolean application) {
        this(pEffect, pDuration, pAmplifier, false, true);
        p = pos;
        origin = pLevel;
        apply = false;
    }

    public GetOutEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible) {
        this(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pVisible);
    }

    public GetOutEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon) {
        this(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pShowIcon, (MobEffectInstance)null, pEffect.createFactorData());
    }

    public GetOutEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon, @Nullable MobEffectInstance pHiddenEffect, Optional<FactorData> pFactorData) {
        super(pEffect,pDuration,pAmplifier,pAmbient,pVisible,pShowIcon);
    }

    public void sendBack(LivingEntity pEntity){
        MinecraftServer minecraftserver = pEntity.level().getServer();
        ServerLevel portalDimension = minecraftserver.getLevel(origin);
        BlockPos portalPlace = p;

        pEntity.changeDimension(portalDimension, new ModPortal(portalPlace, true));
        pEntity.teleportTo(portalPlace.getX(), portalPlace.getY()+1.0f, portalPlace.getZ());
    }

    @Override
    public void applyEffect(LivingEntity pEntity) {
        if (this.hasRemainingDuration()) {
            if(apply) {
                this.getEffect().applyEffectTick(pEntity, 0);
            }else{
                this.getEffect().applyEffectTick(pEntity, 1);
            }
        }
    }

    private boolean hasRemainingDuration() {
        return this.isInfiniteDuration() || this.getDuration() > 0;
    }
}
