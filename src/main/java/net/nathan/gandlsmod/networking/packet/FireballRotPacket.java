package net.nathan.gandlsmod.networking.packet;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.nathan.gandlsmod.Helper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.util.function.Supplier;

public class FireballRotPacket {
    Vec3 LAc;
    public FireballRotPacket(Vec3 LAc){
        this.LAc = LAc;
    }

    public FireballRotPacket(FriendlyByteBuf buf){
        this.LAc = new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(LAc.x);
        buf.writeDouble(LAc.y);
        buf.writeDouble(LAc.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        //Up = 2
        //Down = 3
        //Left = 5
        //Right = 7
        NetworkEvent.Context context= supplier.get();
        context.enqueueWork(() -> {
            //SERVER SIDE
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel().getLevel();
            Entity c = player.getCamera();
            //The movement works but the rotation doesn't?
            //It definitely enters the correct methods
            //c.setDeltaMovement(0.0,1.0,0.0);
            Vec3 LA = c.getLookAngle();
            //You may wonder, why does the "getUpVector" have a parameter?
            //Well turns out the game might disagree with itself and have a new/old version of the rotation in play
            //pPartialTicks is the percentage of the new you want to take in
            //For example, your x was 0 1 tick ago, and 90 now. with a pPartial tick of 0.5, you add half the distance, so x is considered 0.45
            //This is called Linear interpolation (lerp), at 0 you just take the old value, at 1 you take the new one
            //Here I'm using the halfway point

            player.sendSystemMessage(Component.literal("Client Sided LA = " + LAc));
            //The client sided player is the "husk" left behind
            player.sendSystemMessage(Component.literal("Server Sided LA = " + player.getLookAngle()));

            c.setDeltaMovement(LAc.scale(1.0f));
            //c.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(0.0,10000.0,0.0));
            //player.sendSystemMessage(Component.literal("ServerPlayer LA: " + player.getViewVector(0.0f) +"\nServerPlayer Xrot:" + player.getXRot() +"\nServerPlayer Yrot" + player.getYRot()));


        });
        return true;
    }
}
