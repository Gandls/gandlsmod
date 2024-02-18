package net.nathan.gandlsmod.networking.packet;

import net.minecraft.client.Camera;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.network.NetworkEvent;
import net.nathan.gandlsmod.effects.DazeEffect;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;
import net.nathan.gandlsmod.effects.ModEffects;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.List;
import java.util.function.Supplier;

public class Ability2Packet {





    public Ability2Packet(){
    }

    public Ability2Packet(FriendlyByteBuf buf){

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
                    if(playerThirst.getCooldown((byte) 1) <= 0.0f){
                        return;
                    }
                    //This is a warrior using their second ability (STOMP)
                    //It should give nearby entities momentum upward, damage a flat amount? variable?
                    //And give them the slow falling effect for 2 seconds
                    List<Entity> a = pLevel.getEntities(player,player.getBoundingBox().inflate(6.0f));
                    for(Entity b:a){
                        Vec3 res = b.position().subtract(player.position());
                        double dist = Math.max(1.0,res.length());

                        //Take the difference in the player to entity position
                        res = new Vec3(res.x,1.6f,res.y);
                        //Normalize the y value to 2.0
                        res = res.scale(6/(dist*dist));
                        //Reverse scale so closer entities are knocked back more, things 6m away are knocked half their distance
                        //At 6, the force is scaled to 6/(6*6), meaning 1/6 the distance, so a power of 1
                        //At 5, its 6/5, so a power of 1.2
                        //At 4 its 6/4, 1.6
                        //3:2
                        //2:3
                        //1:6
                        //The scale affects both the horizontal and vertical momentum, so close entities are knocked up high
                        b.setDeltaMovement(b.getDeltaMovement().add(res));
                    }
                    playerThirst.setCooldown(15.0f,(byte) 1);
                }
                if(playerThirst.getpIndex() == 3) {
                    if(playerThirst.getCooldown((byte) 0) <= 0.0f){
                        //This is a Gravity Wizard using their "Pull" projectile
                        Chicken s = new Chicken(EntityType.CHICKEN,pLevel);
                        s.moveTo(player.getX(),player.getY()+1.5,player.getZ());
                        s.setDeltaMovement(player.getLookAngle().x * 0.5f, player.getLookAngle().y * 0.5f, player.getLookAngle().z * 0.5f);
                        s.setNoGravity(true);
                        s.setDiscardFriction(true);
                        //s.setNoAi(true);
                        s.addEffect(new MobEffectInstance(ModEffects.PULL.get(),200,0));
                        pLevel.addFreshEntity(s);
                        playerThirst.setCooldown(60,(byte) 1);
                    }
                }
                if(playerThirst.getpIndex() == 4){
                    //This is a Brawler using Dynamite Punch
                    //It should create a line of explosion particles following their look Angle
                    //It should cause knockback and damage to living entities within that 20m rectangular prism
                    //It should also do some knockback to the player using it
                    //Charge based system, 2 punches can be racked up, 20s cooldown

                    //This isn't working
                    //I know it is being run
                    //I know identical code works in the Metal Detector
                    //The context must be the problem
                    //THIS WORKS:

                    //ServerLevel s = (ServerLevel) pLevel;
                    //s.sendParticles(ParticleTypes.FLASH, player.getX(), player.getY() + 1.5f, player.getZ(),10, 0, 0, 0,0);
                    if(playerThirst.getCooldown((byte) 1) == 0.0) {
                        Vec3 up = player.getUpVector(0);
                        Vec3 forward = player.getForward();
                        Vec3 Left = up.cross(forward);
                        Vec3 start = player.getPosition(0);
                        Vec3 end = player.getPosition(0);


                        start = start.add(Left.scale(4.0f));
                        start = start.add(up.scale(-3.0f));
                        start = start.add(forward.scale(0.5f));

                        end = end.add(forward.scale(20.0f));
                        end = end.add(Left.scale(-4.0f));
                        end = end.add(up.scale(3.0f));


                        AABB minMax = new AABB(start, end);
                        player.sendSystemMessage(Component.literal("Start: " + start));
                        player.sendSystemMessage(Component.literal("End: " + end));
                        List<Entity> e = player.level().getEntities(player, minMax);
                        DamageSource n = player.level().damageSources().fellOutOfWorld();
                        for (Entity b : e) {
                            player.sendSystemMessage(Component.literal("Here's an Entity"));
                            b.hurt(n, 6.0f);
                            ((LivingEntity) b).knockback(3.0, -forward.x, -forward.z);
                        }
                        player.knockback(4.0, forward.x, forward.z);
                        playerThirst.setCooldown(20.0f,(byte) 1);
                    }
                }

                if(playerThirst.getpIndex() == 5) {
                    //This is a pyromancer using their drone fireball
                    if(playerThirst.getCooldown((byte) 1) <= 0.0f){
                        return;
                    }
                    LargeFireball WS = new LargeFireball(pLevel, player, 0.0f, 0.0f, 0.0f, 5);
                    WS.moveTo(player.getX(), player.getY() + 2.5, player.getZ());
                    WS.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                    //WS.setDeltaMovement(player.getLookAngle().x * 2f, player.getLookAngle().y * 2f, player.getLookAngle().z * 2f);
                    WS.setDeltaMovement(0,0,0);
                    pLevel.addFreshEntity(WS);
                    player.setCamera(WS);
                    playerThirst.setCooldown(8.0f,(byte) 1);
                    playerThirst.addCheck(5.0f);
                }

                if(playerThirst.getpIndex() == 6){
                    //A warlock using dragons breath
                    player.addEffect(new MobEffectInstance(ModEffects.DRAGONBREATH.get(),200,0));
                }

                if(playerThirst.getpIndex() == 7){
                    if(playerThirst.getCooldown((byte) 2) == 0){
                        //Shaman using Earth Shield
                        player.addEffect(new MobEffectInstance(ModEffects.EARTHSHIELD.get(),240,0));
                        playerThirst.setCooldown(60,(byte) 2);
                    }
                }

                if(playerThirst.getpIndex() == 8){
                    //This is a Rogue Using Evasion
                    if(playerThirst.getCooldown((byte) 2) == 0){
                        player.addEffect(new MobEffectInstance( ModEffects.EVASION.get(),1200,0));
                        playerThirst.setCooldown(120,(byte) 2);
                    }
                }


                ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                        playerThirst), (player));
            });





        });

        return true;
    }

    private boolean hasWaterAroundThem(ServerPlayer player, ServerLevel level, int size) {
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(blockState -> blockState.is(Blocks.WATER)).toArray().length > 0;
    }
}
