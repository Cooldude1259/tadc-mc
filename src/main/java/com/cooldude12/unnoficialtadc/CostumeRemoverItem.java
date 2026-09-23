package com.cooldude12.unnoficialtadc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CostumeRemoverItem extends Item {
    public CostumeRemoverItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Block original = CostumeMap.COSTUMED_TO_VANILLA.get(state.getBlock());
        if (original == null) return InteractionResult.PASS;
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        if (level.getBlockEntity(pos) instanceof CostumeBlockEntity be) {
            ItemStack costume = be.getCostume();
            if (!costume.isEmpty()) {
                level.setBlock(pos, original.defaultBlockState(), 3);
                if (!context.getPlayer().getInventory().add(costume)) {
                    level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY() + 1, pos.getZ(), costume));
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}