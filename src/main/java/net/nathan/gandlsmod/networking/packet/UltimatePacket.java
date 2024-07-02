package net.nathan.gandlsmod.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.nathan.gandlsmod.effects.*;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.particle.ModParticles;
import net.nathan.gandlsmod.sound.ModSounds;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;
import net.nathan.gandlsmod.worldgen.dimension.ModDimensions;
import net.nathan.gandlsmod.worldgen.portal.ModPortal;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class UltimatePacket {

    public UltimatePacket(){
    }

    public UltimatePacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context= supplier.get();
        context.enqueueWork(() -> {

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel().getLevel();
            Level pLevel = player.level();

            //SERVER SIDE


            player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(playerThirst -> {

                if(playerThirst.getpIndex() == 1){
                    if(playerThirst.getCooldown((byte) 3) <= 0){
                        player.addEffect(new ExecuteEffectInstance(ModEffects.EXECUTE.get(),140,0));
                        pLevel.playSeededSound(null,player.getX(),player.getY(),player.getZ(),
                                ModSounds.EXECUTE_SOUND.get(), SoundSource.AMBIENT,1f,1f,0);
                        playerThirst.setCooldown(180,(byte) 3);
                    }
                }
                if(playerThirst.getpIndex() == 3) {
                    //Change dimension
                    player.sendSystemMessage(Component.literal("Attempting dimension change"));
                    if (player.level() instanceof ServerLevel serverlevel) {
                        MinecraftServer minecraftserver = serverlevel.getServer();
                        ResourceKey<Level> resourcekey = ModDimensions.GANDLSDIM_LEVEL_KEY;

                        ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
                        BlockPos portalPlace = player.getOnPos();
                        List<Entity> b = pLevel.getEntities(player,player.getBoundingBox().inflate(20));
                        if(portalDimension != null) {
                            for (Entity s : b) {
                                if(s instanceof LivingEntity) {

                                    ((LivingEntity) s).addEffect(new GetOutEffectInstance(ModEffects.GETOUT.get(),400,0,s.getOnPos(), s.level().dimension()));

                                    portalPlace = s.getOnPos();
                                    portalPlace = portalPlace.offset(0, -portalPlace.getY() + 16, 0);
                                    s.changeDimension(portalDimension, new ModPortal(portalPlace, true));
                                    s.teleportTo(portalPlace.getX(), portalPlace.getY(), portalPlace.getZ());
                                }
                            }
                        }
                        portalPlace = player.getOnPos();

                        if (portalDimension != null && !player.isPassenger()) {
                            if (resourcekey == ModDimensions.GANDLSDIM_LEVEL_KEY) {
                                portalPlace = portalPlace.offset(0,-portalPlace.getY()+16,0);
                                player.addEffect(new GetOutEffectInstance(ModEffects.GETOUT.get(),400,0,player.getOnPos(), player.level().dimension(), false));
                                player.changeDimension(portalDimension, new ModPortal(portalPlace,true));
                                //Adding this line works (but it shouldn't....whatever)
                                player.teleportTo(portalPlace.getX(),portalPlace.getY(),portalPlace.getZ());
                                playerThirst.setCooldown(600,(byte) 3);
                            } else {
                                //This is for coming back out, which is now handled by the GetOutEffectInstance
                                /*
                                player.changeDimension(portalDimension, new ModPortal(portalPlace,false));
                                boolean x = false;
                                int y = 255;
                                for(int i = 255;i > 0;i--){
                                    if(!x){
                                        BlockState a = portalDimension.getBlockState(new BlockPos(portalPlace.getX(),i,portalPlace.getZ()));
                                        x = !a.is(Blocks.AIR);
                                        y= i;
                                    }
                                }
                                player.teleportTo(portalPlace.getX(),y,portalPlace.getZ());

                                 */

                            }
                        }
                    }
                }

                if(playerThirst.getpIndex() == 4){
                    //Brawler using Eight Gates
                    player.sendSystemMessage(Component.literal("I register the ultimate packet and im a brawler"));
                    if(player.getEffect(ModEffects.EIGHTGATES.get()) != null){
                        player.sendSystemMessage(Component.literal("I have eight gates"));
                        //If the brawler already has eight Gates active, interpret this packet as the opening of another gate
                        ((EightGatesInstance)player.getEffect(ModEffects.EIGHTGATES.get())).openGate();
                        pLevel.playSeededSound(null,player.getX(),player.getY(),player.getZ(),
                                ModSounds.EIGHT_GATES_SOUND.get(), SoundSource.AMBIENT,1f,1f,0);
                    }else{
                        player.sendSystemMessage(Component.literal("Adding Eight Gates effect"));
                        player.addEffect(new EightGatesInstance(ModEffects.EIGHTGATES.get(),600,1));
                        pLevel.playSeededSound(null,player.getX(),player.getY(),player.getZ(),
                                ModSounds.EIGHT_GATES_SOUND.get(), SoundSource.AMBIENT,1f,1f,0);
                    }
                }

                if(playerThirst.getpIndex() == 5){
                    if(playerThirst.getCooldown((byte) 3) <= 0){
                        player.addEffect(new MobEffectInstance(ModEffects.BREATHEFIREBALL.get(),140,0));
                        player.addEffect(new MobEffectInstance(MobEffects.LEVITATION,140,0));

                        playerThirst.setCooldown(180,(byte) 3);
                    }
                }
                if(playerThirst.getpIndex() == 6){
                    if(playerThirst.getCooldown((byte) 3) <= 0){
                        player.addEffect(new MobEffectInstance(ModEffects.SCARYEXPRESSION.get(),300,0));
                        pLevel.playSeededSound(null,player.getX(),player.getY(),player.getZ(),
                                ModSounds.SCARY_EXPRESSION_SOUND.get(), SoundSource.AMBIENT,1f,1f,0);

                        playerThirst.setCooldown(180,(byte) 3);
                    }
                }
                if(playerThirst.getpIndex() == 7){
                    if(playerThirst.getCooldown((byte) 3) == 0){
                        Wolf w = new Wolf(EntityType.WOLF,pLevel);
                        w.setTame(true);
                        w.setOwnerUUID(player.getUUID());
                        w.addEffect(new MobEffectInstance(ModEffects.PINGDEATHLESS.get(),300,0));
                        w.addEffect(new MobEffectInstance(MobEffects.REGENERATION,300,10));
                        pLevel.playSeededSound(null,player.getX(),player.getY(),player.getZ(),
                                ModSounds.WOLFSUMMON_SOUND.get(), SoundSource.AMBIENT,1f,1f,0);
                        pLevel.addFreshEntity(w);
                        playerThirst.setCheck(180);
                    }
                }
                if(playerThirst.getpIndex() == 8){
                    if(playerThirst.getCooldown((byte) 3) == 0){
                        player.addEffect(new MobEffectInstance(ModEffects.KNIVES.get(),200,0));
                        pLevel.playSeededSound(null,player.getX(),player.getY(),player.getZ(),
                                ModSounds.NIGHT_OF_KNIVES_SOUND.get(), SoundSource.AMBIENT,1f,1f,0);
                    }
                    playerThirst.setCooldown(240,(byte) 3);
                }




                ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                        playerThirst), (player));
            });





        });

        return true;
    }
}
