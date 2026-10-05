package com.cooldude12.unnoficialtadc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TrimExtractorBlock extends Block {
    public TrimExtractorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        UNOFFICIALTADCMCServer.LOGGER.info("TrimExtractorBlock used by player: " + player.getName().getString() + " with item: " + stack.getItem().toString());

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        // Server side logic
        UNOFFICIALTADCMCServer.LOGGER.info("Processing TrimExtractorBlock interaction on server side.");

        return InteractionResult.PASS;
    }
}