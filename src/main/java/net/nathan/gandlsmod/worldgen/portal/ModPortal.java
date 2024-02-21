package net.nathan.gandlsmod.worldgen.portal;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class ModPortal implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;

    public ModPortal(BlockPos pos, boolean insideDim) {
        thisPos = pos;
        insideDimension = insideDim;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        entity.sendSystemMessage(Component.literal("I'm in the placeEntity method"));
        //This logic seems to be ignored
        // The player transporting always ends up in their roughly same position in the other dimension
        //But they're positions get rounded out so possibly still using blockpos for transport logic?

        //Is something else moving them after this is called?
        int y = 1;

        if (!insideDimension) {
            y = 1;
        }
        BlockPos destinationPos = new BlockPos(thisPos.getX(), y, thisPos.getZ());

        /*
        int tries = 0;
        while ((destinationWorld.getBlockState(destinationPos).getBlock() != Blocks.AIR) &&
                !destinationWorld.getBlockState(destinationPos).canBeReplaced(Fluids.WATER) &&
                (destinationWorld.getBlockState(destinationPos.above()).getBlock()  != Blocks.AIR) &&
                !destinationWorld.getBlockState(destinationPos.above()).canBeReplaced(Fluids.WATER) && (tries < 25)) {
            destinationPos = destinationPos.above(2);
            tries++;
        }

         */

        entity.setPos(destinationPos.getX(), 16, destinationPos.getZ());


        return entity;
    }
}
