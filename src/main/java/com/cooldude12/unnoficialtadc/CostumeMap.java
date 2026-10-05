package com.cooldude12.unnoficialtadc;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public final class CostumeMap {
    private CostumeMap() {}

    // Public fields so CostumeItem / CostumeRemoverItem keep working unchanged.
    // Treat them as read-only from outside; add entries through register().
    public static final Map<Block, Block> VANILLA_TO_COSTUMED = new HashMap<>();
    public static final Map<Block, Block> COSTUMED_TO_VANILLA = new HashMap<>();

    /**
     * Registers a vanilla block and its costumed wrapper as a pair.
     * Both maps are filled together so they can never disagree.
     * Call this during mod initialisation, from any mod that depends on tadc-mc.
     */
    public static void register(Block vanilla, Block costumed) {
        if (VANILLA_TO_COSTUMED.containsKey(vanilla)) {
            throw new IllegalStateException("Block already has a costumed version: " + vanilla);
        }
        if (COSTUMED_TO_VANILLA.containsKey(costumed)) {
            throw new IllegalStateException("Costumed block already mapped: " + costumed);
        }
        VANILLA_TO_COSTUMED.put(vanilla, costumed);
        COSTUMED_TO_VANILLA.put(costumed, vanilla);
    }

    /** tadc-mc's own pairs. Called from onInitialize, after ModBlocks and ModBlockEntities. */
    public static void initialize() {
        register(Blocks.STONE, ModBlocks.COSTUMED_STONE);
        register(Blocks.GRASS_BLOCK, ModBlocks.COSTUMED_GRASS_BLOCK);
    }
}