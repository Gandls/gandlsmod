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
import net.minecraft.world.entity.Pose;
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
import net.minecraftforge.event.entity.living.LivingEvent;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.networking.packet.DrinkWaterPacket;
import net.nathan.gandlsmod.networking.packet.ThirstDataSyncSToC;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;

import java.util.Set;

public class Ability1 extends Item {
    public Ability1(Properties pProperties) {
        super(pProperties);
    }



    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Vec3 LA = pPlayer.getLookAngle();
        pPlayer.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
            pPlayer.sendSystemMessage(Component.literal("Class: " + thirst.getpIndex()));
            //Since you changed data client side, you must tell the server about the changes




        });


        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    private void outputValuableCoordinate(BlockPos blockPos, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Found " + I18n.get(block.getDescriptionId()) + " at "
                + "(" + blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ()+ ")"));
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
