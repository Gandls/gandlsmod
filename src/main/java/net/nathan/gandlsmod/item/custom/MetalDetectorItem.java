package net.nathan.gandlsmod.item.custom;


import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.nathan.gandlsmod.Helper;
import net.nathan.gandlsmod.particle.ModParticles;
import net.nathan.gandlsmod.sound.ModSounds;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.util.Set;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Properties pProperties) {
        super(pProperties);
    }



    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide()){
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            if(player != null) {
                spawnFoundParticles(pContext, positionClicked);
                /*
                player.setDeltaMovement(
                        new Vec3(
                                player.getLookAngle().x * 5f,
                                player.getLookAngle().y*5f,
                                player.getLookAngle().z*5f) );
                 */


                //HOW TO IMPLEMENT "TAGS"
                /*
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                    thirst.addThirst(1);
                player.sendSystemMessage(Component.literal("Current Thirst: " + thirst.getThirst()));
                });
                */

                //Upon using on a block, get the players ThirstProvider capability

                //Set its class
            }

            boolean foundBlock = false;

            for(int i=0;i<=positionClicked.getY() + 64; i++){
                //This will check the y level of the block interacted with
                //And keep going down, you may ask "why is positionClicked.Y +64 why + 64?"
                //Remember that now the y level can go negative, since I is set to 0 we need to account for the
                //player clicking on a block like at -64
                //at the start of the for loop, the y position should be incremented by 64
                BlockState state = pContext.getLevel().getBlockState(positionClicked.below(i));
                if(isValuableBlock(state)){
                    outputValuableCoordinate(positionClicked.below(i),player,state.getBlock());
                    foundBlock = true;

                    pContext.getLevel().playSeededSound(null,positionClicked.getX(),positionClicked.getY(),positionClicked.getZ(),
                            ModSounds.SPIN_SOUND.get(),SoundSource.BLOCKS,1f,1f,0);

                    break;
                }
            }
            if(!foundBlock){
                player.sendSystemMessage(Component.literal("No Valuables Found"));
            }

            //This damages the item used in the hand
            pContext.getItemInHand().hurtAndBreak(
                    1
                    ,pContext.getPlayer()
                    ,player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.getCooldowns().addCooldown(this, 2);

        /*
        if (!pLevel.isClientSide) {
            ThrownEnderpearl thrownenderpearl = new ThrownEnderpearl(pLevel, pPlayer);
            thrownenderpearl.setItem(itemstack);
            thrownenderpearl.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(thrownenderpearl);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        */

        Vec3 lA = new Vec3(Math.random(),Math.random(),Math.random()).normalize();
        //pPlayer.sendSystemMessage(Component.literal(lA.toString()));
        Vec3 t = pPlayer.getLookAngle();

        Vec3 z = pPlayer.getUpVector(0);

        double r = Math.random()*180;
        t = Helper.rotate(t,lA,r);
        z = Helper.rotate(z,lA,r);
        //Get the cross product
        Vec3 cross = lA.cross(t);
        //Problem: The cross is always y=0 since x/z are 0, meaning the cross is in the xz plane
        //if the cross is always in the x/z plane then its always 90 degrees to t
        //If its always 90 to t, then t will get rotated down to the x/z plane
        //pPlayer.sendSystemMessage(Component.literal(cross.toString()));
        for(int i=0;i<36;i++){
            Vec3 rot = rotate(z,t,i*10).normalize();
            pLevel.addParticle(ParticleTypes.FLAME,
                    rot.x*3f + pPlayer.position().x,
                    rot.y*3f+ pPlayer.position().y+pPlayer.getEyeHeight(),
                    rot.z*3f+ pPlayer.position().z,
                    0,
                    0,
                    0);
        }

        pPlayer.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {

            if(thirst.getpIndex() != 1){
                pLevel.playSound(pPlayer,pPlayer.blockPosition(), SoundEvents.NOTE_BLOCK_CHIME.get(), SoundSource.BLOCKS, 1f, 1f);

                /*
                pPlayer.setDeltaMovement(
                        new Vec3(
                                pPlayer.getLookAngle().x * 10f,
                                pPlayer.getLookAngle().y*10f,
                                pPlayer.getLookAngle().z*10f) );
                 */

                /*
                X + Y + Z = player x,y,z
                To get a perpendicular angle in a 3d space, their cross product must be 0
                Such that (X1 * X2) + (Y1 * Y2) + (Z1 * Z2) = 0
                And since the ring is a set size, X2^2 + Y2^2 + Z2^2 = 1
                Try an example: the player is looking directly up, their lookangle is 0,1,0
                Since both x and z are 0, anything on the x-z plane is perpendicular as long as y2 = 0
                In our case, we only need 1 perpendicular vector, then we can rotate it about the lookangle Axis
                To expedite this, we'll do this: set one vector to 0, then equal out the others
                For example: (7,13,5)
                Set z to 0 - (7,13)
                Now multiply the lower number by the negative division like -13/7 such that 7 * -13/7 = -13
                And reverse it, so -7/13 * 13 = -7
                Both vectors (-13/7,1,0) and (1,-7/13,0) should be perpendicular to (7,13,5)
                 */

                //This code sort of works


                //Since this is a vector known to be perpendicular (it's dot product with lA is zero)
                //It will be rotated around the LA to create a ring
                Vec3 perp = new Vec3(-lA.y/ lA.x,lA.y,0).normalize().scale(0.5f);

                /*
                for(int i=1;i<21;i++){
                    Vec3 rot = rotate(perp,lA,i*18).normalize().scale(0.5f);
                    pLevel.addParticle(ParticleTypes.FLAME,
                            pPlayer.getX() + lA.x,
                            pPlayer.getY()+2.0f + lA.y,
                            pPlayer.getZ() + lA.z,
                            rot.x,
                            rot.y,
                            rot.z);
                }

                 */




            }else {

            }
        });

        //pPlayer.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(40.00f);
        //pPlayer.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(40.00f);

        //pPlayer.sendSystemMessage(Component.literal("Base health is now: " + pPlayer.getAttribute(Attributes.MAX_HEALTH)));


        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    private void outputValuableCoordinate(BlockPos blockPos, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Found " + I18n.get(block.getDescriptionId()) + " at "
        + "(" + blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ()+ ")"));
    }


    private void spawnFoundParticles(UseOnContext pContext, BlockPos positionClicked) {
        for(int i = 0; i < 360; i++) {
            if(i % 20 == 0) {
                pContext.getLevel().addParticle(ModParticles.DEATH_PARTICLES.get(),
                        positionClicked.getX() + 0.5d, positionClicked.getY() + 1, positionClicked.getZ() + 0.5d,
                        Math.cos(i) * 0.15d, 0.15d, Math.sin(i) * 0.15d);
            }
        }
    }
    private boolean isValuableBlock(BlockState state) {
        return state.is(Blocks.IRON_ORE);
    }

    public static Vec3 rotate(Vec3 toRotate, Vec3 around, double angle) {

        //When LA Y > 0, the particle come towards the player
        //When LA y < 0 the particles go away from the player (with the LA)
        Vec3 AParaB = around.normalize().scale(toRotate.dot(around) / around.dot(around));
        Vec3 APerpB = new Vec3(toRotate.x - AParaB.x,toRotate.y - AParaB.y,toRotate.z - AParaB.z);
        Vec3 w = around.cross(toRotate);
        double x1  = Math.cos(Math.toRadians(angle)) / APerpB.length();
        double x2 = Math.sin(Math.toRadians(angle)) / w.length();
        Vec3 APerpScaled = APerpB.scale(x1);
        w = w.scale(x2);
        Vec3 Add = new Vec3(APerpScaled.x + w.x,APerpScaled.y + w.y,APerpScaled.z + w.z).scale(APerpB.length());
        Vec3 total = new Vec3(Add.x + AParaB.x,Add.y + AParaB.y,Add.z + AParaB.z);

        return total;
    }

}
