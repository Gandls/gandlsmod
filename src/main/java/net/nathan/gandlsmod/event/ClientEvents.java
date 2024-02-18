package net.nathan.gandlsmod.event;

import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.EffectCommands;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nathan.gandlsmod.GandlsMod;
import net.nathan.gandlsmod.client.ClientThirstData;
import net.nathan.gandlsmod.client.ThirstHUDOverlay;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.networking.packet.*;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;
import net.nathan.gandlsmod.util.KeyBinding;

import java.util.List;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = GandlsMod.MOD_ID, value = Dist.CLIENT)
    public  static class ClientForgeEvents {

        public static void onMouseDrag(InputEvent event){

        }
        @SubscribeEvent
        public static void onKeyInput(InputEvent event){
            if(KeyBinding.DRINKING_KEY.consumeClick()){
                ModMessages.sendtoServer(new DrinkWaterPacket());
            }
            if(KeyBinding.ABILITY_2.consumeClick()){
                if(ClientThirstData.getPlayerIndex() != 5){
                    ModMessages.sendtoServer(new Ability2Packet());
                }
                if(ClientThirstData.getPlayerIndex() == 5){
                    if(ClientThirstData.getC2() <= 0.0f){
                        Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_FRONT);
                        ModMessages.sendtoServer(new Ability2Packet());
                    }
                }
                //ModMessages.sendtoServer(new Ability2Packet());
            }
            if(KeyBinding.ABILITY_3.consumeClick()){
                ModMessages.sendtoServer(new Ability3Packet());
            }

            if(KeyBinding.ULTIMATE.consumeClick()){
                ModMessages.sendtoServer(new UltimatePacket());
            }



        }
        @SubscribeEvent
        public static void onFOV(ViewportEvent.ComputeFov event){
            if(ClientThirstData.getPlayerIndex() == 5){
                if(ClientThirstData.getCheck() > 0.0f){
                    event.setFOV(90);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerMove(MovementInputUpdateEvent event){
            event.getEntity().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                if(ClientThirstData.getPlayerIndex() == 5) {
                    //If the player is a pyromancer
                    if(ClientThirstData.getCheck() > 0.0f) {
                        //And the fireball camera is active
                        //Get the Client Sided LA
                        Vec3 LAc = event.getEntity().getLookAngle();
                        ModMessages.sendtoServer(new FireballRotPacket(LAc));
                    }
                }
            });
            }

            //Up is 2
            //Down is 3
            //Left is 5
            //Right is 7

        }

    @Mod.EventBusSubscriber(modid = GandlsMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public  static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){

            event.register(KeyBinding.DRINKING_KEY);
            event.register(KeyBinding.ABILITY_2);
            event.register(KeyBinding.ABILITY_3);
            event.register(KeyBinding.ULTIMATE);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("thirst", ThirstHUDOverlay.HUD_THIRST);
        }
    }
}
