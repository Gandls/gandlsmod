package net.nathan.gandlsmod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CheckParticles extends TextureSheetParticle{
    CheckParticles(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ);
        this.friction = 0.8f;
        this.quadSize *= 1.85f;
        this.lifetime = 40;

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }

    public void tick() {
        super.tick();
        if (!this.removed && !this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).is(FluidTags.WATER)) {
            this.remove();
        }

    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            CheckParticles bubblecolumnupparticle = new CheckParticles(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            bubblecolumnupparticle.pickSprite(this.sprite);
            return bubblecolumnupparticle;
        }
    }
}
