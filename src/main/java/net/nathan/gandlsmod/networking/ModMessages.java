package net.nathan.gandlsmod.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.nathan.gandlsmod.GandlsMod;
import net.nathan.gandlsmod.networking.packet.Ability2Packet;
import net.nathan.gandlsmod.networking.packet.C2SPacket;
import net.nathan.gandlsmod.networking.packet.DrinkWaterPacket;
import net.nathan.gandlsmod.networking.packet.ThirstDataSyncSToC;

import javax.print.DocFlavor;
import javax.swing.*;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net  = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(GandlsMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;
        net.messageBuilder(C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SPacket::new)
                .encoder(C2SPacket::toBytes)
                .consumerMainThread(C2SPacket::handle)
                .add();
        net.messageBuilder(DrinkWaterPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DrinkWaterPacket::new)
                .encoder(DrinkWaterPacket::toBytes)
                .consumerMainThread(DrinkWaterPacket::handle)
                .add();
        net.messageBuilder(Ability2Packet.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(Ability2Packet::new)
                .encoder(Ability2Packet::toBytes)
                .consumerMainThread(Ability2Packet::handle)
                .add();
        net.messageBuilder(ThirstDataSyncSToC.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ThirstDataSyncSToC::new)
                .encoder(ThirstDataSyncSToC::toBytes)
                .consumerMainThread(ThirstDataSyncSToC::handle)
                .add();

    }

    public static <MSG> void sendtoServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
