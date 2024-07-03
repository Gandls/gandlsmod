package net.nathan.gandlsmod.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nathan.gandlsmod.GandlsMod;
import net.nathan.gandlsmod.particle.ModParticles;
import net.nathan.gandlsmod.particle.custom.CheckParticles;
import net.nathan.gandlsmod.particle.custom.DeathParticles;
import net.nathan.gandlsmod.particle.custom.ScaryParticles;

@Mod.EventBusSubscriber(modid = GandlsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestEvents {


}
