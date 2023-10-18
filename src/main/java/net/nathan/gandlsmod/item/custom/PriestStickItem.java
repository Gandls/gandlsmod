package net.nathan.gandlsmod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;

public class PriestStickItem extends Item {
    public PriestStickItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.getCooldowns().addCooldown(this, 20);

        pPlayer.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
            if(thirst.getpIndex() != 0){
                pPlayer.sendSystemMessage(Component.literal("Player already has a class"));
            }else {
                thirst.setPClass(2);
            }
        });

        //pPlayer.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(40.00f);
        //pPlayer.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(40.00f);

        //pPlayer.sendSystemMessage(Component.literal("Base health is now: " + pPlayer.getAttribute(Attributes.MAX_HEALTH)));


        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
