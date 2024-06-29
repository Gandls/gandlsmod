package net.nathan.gandlsmod.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nathan.gandlsmod.GandlsMod;
import net.nathan.gandlsmod.particle.ModParticles;
import net.nathan.gandlsmod.particle.custom.DeathParticles;

@Mod.EventBusSubscriber(modid = GandlsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestEvents {

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event){
        //Minecraft.getInstance().particleEngine.register(ModParticles.DEATH_PARTICLES.get(), DeathParticles.Provider::new);
        //Okay this is straight up not being run
        Minecraft.getInstance().particleEngine.register(ModParticles.DEATH_PARTICLES.get(), DeathParticles.Provider::new);
    }
}
