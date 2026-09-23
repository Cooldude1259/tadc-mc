package com.cooldude12.unnoficialtadc;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;
import java.util.stream.Collectors;

public class CostumeMap {
    public static final Map<Block, Block> VANILLA_TO_COSTUMED = Map.of(
            Blocks.STONE, ModBlocks.COSTUMED_STONE,
            // add more pairs as you build more wrapper blocks
            Blocks.GRASS_BLOCK, ModBlocks.COSTUMED_GRASS_BLOCK
    );
    public static final Map<Block, Block> COSTUMED_TO_VANILLA =
            VANILLA_TO_COSTUMED.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
}