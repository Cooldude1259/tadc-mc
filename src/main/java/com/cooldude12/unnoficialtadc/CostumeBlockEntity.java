package com.cooldude12.unnoficialtadc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CostumeBlockEntity extends BlockEntity {
    public CostumeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COSTUME_BLOCK_ENTITY, pos, state);
    }
}