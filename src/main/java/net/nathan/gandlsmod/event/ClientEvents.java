package net.nathan.gandlsmod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nathan.gandlsmod.GandlsMod;
import net.nathan.gandlsmod.client.ThirstHUDOverlay;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.networking.packet.Ability2Packet;
import net.nathan.gandlsmod.networking.packet.C2SPacket;
import net.nathan.gandlsmod.networking.packet.DrinkWaterPacket;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;
import net.nathan.gandlsmod.util.KeyBinding;

import java.util.List;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = GandlsMod.MOD_ID, value = Dist.CLIENT)
    public  static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent event){
            if(KeyBinding.DRINKING_KEY.consumeClick()){
                ModMessages.sendtoServer(new DrinkWaterPacket());
            }
            if(KeyBinding.ABILITY_2.consumeClick()){
                ModMessages.sendtoServer(new Ability2Packet());
            }
        }

        @SubscribeEvent
        public static void onPlayerMove(PlayerFlyableFallEvent event){
            event.getEntity().startFallFlying();
        }
    }

    @Mod.EventBusSubscriber(modid = GandlsMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public  static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){

            event.register(KeyBinding.DRINKING_KEY);
            event.register(KeyBinding.ABILITY_2);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("thirst", ThirstHUDOverlay.HUD_THIRST);
        }
    }
}
