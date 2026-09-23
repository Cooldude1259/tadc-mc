package com.cooldude12.unnoficialtadc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CostumeItem extends Item {
    public CostumeItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Block wrapper = CostumeMap.VANILLA_TO_COSTUMED.get(state.getBlock());
        if (wrapper == null) return InteractionResult.PASS;
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        ItemStack held = context.getItemInHand();
        ItemStack costumeCopy = held.copyWithCount(1);
        Block original = state.getBlock();

        level.setBlock(pos, wrapper.defaultBlockState(), 3);
        if (level.getBlockEntity(pos) instanceof CostumeBlockEntity be) {
            be.setCostume(costumeCopy);
            be.setOriginalBlock(original);
        }
        held.shrink(1);
        return InteractionResult.SUCCESS;
    }
}