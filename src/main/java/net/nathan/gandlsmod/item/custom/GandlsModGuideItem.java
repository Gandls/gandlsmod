package net.nathan.gandlsmod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;
import org.checkerframework.checker.units.qual.C;

import javax.annotation.Nullable;
import java.io.Console;
import java.util.List;

public class GandlsModGuideItem extends WrittenBookItem {

    public GandlsModGuideItem(Item.Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        //YESSSS!!!!
        //WORKS!!!!
            CompoundTag CT = new CompoundTag();
            //It's a null problem
            //Creating the itemstack is the issue
            ItemStack it = new ItemStack(Items.WRITTEN_BOOK);
            CT = new CompoundTag();
            CT.put("author", StringTag.valueOf("SARMA"));
            CT.put("title",StringTag.valueOf("Karma"));
            ListTag LT = new ListTag();
            LT.add(0, StringTag.valueOf("Page1"));
            CT.put("pages", LT);
            CT.putBoolean("resolved",false);
            it.setTag(CT);
            pContext.getPlayer().addItem(it);


        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.is(Blocks.LECTERN)) {
            return LecternBlock.tryPlaceBook(pContext.getPlayer(), level, blockpos, blockstate, pContext.getItemInHand()) ? InteractionResult.sidedSuccess(level.isClientSide) : InteractionResult.PASS;
        } else {
            return InteractionResult.PASS;
        }
    }

}
