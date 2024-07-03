package net.nathan.gandlsmod.networking.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;
import net.nathan.gandlsmod.client.ClientThirstData;
import net.nathan.gandlsmod.thirst.PlayerThirst;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;

import java.util.function.Supplier;

public class ThirstDataSyncSToC {
    private final int thirst,pIndex;
    private final float c1,c2,c3,c4,bonusDamage,check;
    private final float maxC1,maxC2,maxC3,maxC4;
    private final boolean emp,dazed;
    private static final String MESSAGE_DRINK_WATER = "message.gandlsmod.drink_water";
    private static final String MESSAGE_NO_WATER = "message.gandlsmod.no_water";
    public ThirstDataSyncSToC(int thirst, float c1, float c2, float c3, float c4, int pIndex,float bonusDamage,float check, boolean emp, boolean dazed, float maxC1,float maxC2,float maxC3,float maxC4){
    this.thirst = thirst;
    this.c1 = c1;
    this.c2 = c2;
    this.c3 = c3;
    this.c4 = c4;
    this.pIndex = pIndex;
    this.bonusDamage = bonusDamage;
    this.check = check;
    this.emp = emp;
    this.dazed = dazed;
    this.maxC1 = maxC1;
    this.maxC2 = maxC2;
    this.maxC3 = maxC3;
    this.maxC4 = maxC4;
    }

    public ThirstDataSyncSToC(PlayerThirst p){

        this.c1 = p.getCooldown((byte) 0);
        this.c2 = p.getCooldown((byte) 1);
        this.c3 = p.getCooldown((byte) 2);
        this.c4 = p.getCooldown((byte) 3);
        this.pIndex = p.getpIndex();
        this.thirst = p.getThirst();
        this.bonusDamage = p.getBonusDamage();
        this.check = p.getCheck();
        this.emp = p.getEmpowered();
        this.dazed = p.getDazed();
        this.maxC1 = p.getMAXCooldown((byte) 0);
        this.maxC2 = p.getMAXCooldown((byte) 1);
        this.maxC3 = p.getMAXCooldown((byte) 2);
        this.maxC4 = p.getMAXCooldown((byte) 3);
    }

    public ThirstDataSyncSToC(FriendlyByteBuf buf){
        //ORDER MATTERS HERE! It must match the same order of the buf reading below
        this.thirst = buf.readInt();
        this.c1 = buf.readFloat();
        this.c2 = buf.readFloat();
        this.c3 = buf.readFloat();
        this.c4 = buf.readFloat();
        this.pIndex = buf.readInt();
        this.bonusDamage = buf.readFloat();
        this.check = buf.readFloat();
        this.emp = buf.readBoolean();
        this.dazed = buf.readBoolean();
        this.maxC1 = buf.readFloat();
        this.maxC2 = buf.readFloat();
        this.maxC3 = buf.readFloat();
        this.maxC4 = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf){

        buf.writeInt(thirst);
        buf.writeFloat(c1);
        buf.writeFloat(c2);
        buf.writeFloat(c3);
        buf.writeFloat(c4);
        buf.writeInt(pIndex);
        buf.writeFloat(bonusDamage);
        buf.writeFloat(check);
        buf.writeBoolean(emp);
        buf.writeBoolean(dazed);
        buf.writeFloat(maxC1);
        buf.writeFloat(maxC2);
        buf.writeFloat(maxC3);
        buf.writeFloat(maxC4);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context= supplier.get();
        context.enqueueWork(() -> {
            //HERE WE ARE ON THE CLIENT
            ClientThirstData.set(thirst,c1,c2,c3,c4,pIndex,bonusDamage,check,emp,dazed,maxC1,maxC2,maxC3,maxC4);

        });
        return true;
    }

    private boolean hasWaterAroundThem(ServerPlayer player, ServerLevel level, int size) {
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(blockState -> blockState.is(Blocks.WATER)).toArray().length > 0;
    }
}
