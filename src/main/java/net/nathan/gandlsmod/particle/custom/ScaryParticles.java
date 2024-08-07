package net.nathan.gandlsmod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ScaryParticles extends TextureSheetParticle {
    protected ScaryParticles(ClientLevel level, double x, double y, double z, SpriteSet s, double xd, double yd, double zd){
        super(level,x,y,z,xd,yd,zd);

        this.friction = 0.8f;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.quadSize *= 1.85f;
        this.lifetime = 40;
        this.setSpriteFromAge(s);

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;

    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick(){
        super.tick();
    }
    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet spriteSet){
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz){
            return new ScaryParticles(level,x,y,z,this.sprites,dx,dy,dz);
        }
    }




}
