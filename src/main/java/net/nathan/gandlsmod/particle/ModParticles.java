package net.nathan.gandlsmod.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nathan.gandlsmod.GandlsMod;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, GandlsMod.MOD_ID);

    //The name "death_particle" is the ONLY one that works
    //Put that name anywhere and it will function, regardless of the provider, the registryObject, the provider used

    public static final RegistryObject<SimpleParticleType> DEATH_PARTICLES = PARTICLE_TYPES.register("death_particle",
            ()-> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> DEATH_PARTICLES_LONG = PARTICLE_TYPES.register("death_long_particle",
            ()-> new SimpleParticleType(true));



    public static final RegistryObject<SimpleParticleType> SCARY_EXPRESSION = PARTICLE_TYPES.register("scary_particle",
            ()->new SimpleParticleType(true));



    public static final RegistryObject<SimpleParticleType> CHECK_PARTICLES = PARTICLE_TYPES.register("check_particle",
            ()-> new SimpleParticleType(true));
    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
