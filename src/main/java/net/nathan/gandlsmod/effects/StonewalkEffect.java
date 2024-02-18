package net.nathan.gandlsmod.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.StonecutterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.nathan.gandlsmod.block.ModBlocks;

import java.util.List;
import java.util.stream.Stream;

public class StonewalkEffect extends MobEffect {

    public StonewalkEffect(MobEffectCategory mEC, int color){
        super(mEC,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){
        Level pLevel = pLivingEntity.level();
        //Client side effects
        if(pLevel.isClientSide()){
            //With each tick of the effect, do X

        }else{
            //Server Side Effects
            //Get nearby blocks
            BlockPos pPos = pLivingEntity.getOnPos();
            if (pLivingEntity.onGround()) {
                BlockState blockstate = ModBlocks.CRUMBLE_BLOCK.get().defaultBlockState();
                int i = 4;
                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for(BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-i, 0, -i), pPos.offset(i, 0, i))) {
                    if (blockpos.closerToCenterThan(pLivingEntity.position(), (double)i)) {
                        blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY(), blockpos.getZ());
                        BlockState blockstate1 = pLevel.getBlockState(blockpos$mutableblockpos);
                        if(blockstate1 == Blocks.LAVA.defaultBlockState()){
                            pLevel.setBlockAndUpdate(blockpos, blockstate);
                        }

                    }
                }

            }
        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
