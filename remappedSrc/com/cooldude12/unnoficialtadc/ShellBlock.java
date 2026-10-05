// ============================================================
// LOCATION: src/main/java/com/cooldude12/unnoficialtadc/ShellBlock.java
// This file exists here, in the mod's main source set, and is
// currently NOT excluded from compilation or the built jar
// (the build.gradle exclude line for it targets "ShellBlocks.java",
// a typo -- the real file is "ShellBlock.java" -- and even a correctly
// spelled exclude here wouldn't work anyway, since processResources
// only filters resources, not compiled Java source/bytecode).
// ============================================================
package com.cooldude12.unnoficialtadc;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * ShellBlock — the first stage of regrowth.
 *
 * Concept: this block represents land that has started recovering but hasn't
 * fully healed yet. Left alone, it slowly matures (AGE 0 -> MAX_AGE) and then
 * converts itself into ModBlocks.RESTORED. If a meteor strike (or anything
 * else) wants to set recovery back, it just needs to place this block again
 * at AGE 0 (or replace it with the contaminated block entirely — that part
 * comes later).
 *
 * This mirrors how vanilla crops/saplings mature (they use an IntegerProperty
 * called "age" and a randomTick that increments it), just repurposed here as
 * a "healing" stage instead of a "growing" stage.
 */
public class ShellBlock extends Block {

    // How many growth stages this block has before it matures into RESTORED.
    // 0 = freshly-formed shell, MAX_AGE = about to convert.
    public static final int MAX_AGE = 3;

    // A property that stores the current age (0 to MAX_AGE) directly on the
    // block's state. Minecraft blocks themselves are stateless singletons —
    // all the "which stage is this specific block in" data lives in the
    // BlockState, not the Block instance. This is the same pattern vanilla
    // uses for crops (see CropBlock.AGE).
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3; // built-in 0-3 property, saves us declaring our own

    public ShellBlock(BlockBehaviour.Properties properties) {
        // .randomTicks() is what makes randomTick() below actually get called.
        // Without it, random ticks are skipped entirely for this block —
        // this is the same flag vanilla uses for crops, saplings, etc.
        super(properties.randomTicks());

        // Set the default state (what you get when the block is placed with
        // no other logic involved) to AGE = 0, i.e. freshly formed shell.
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    // Required whenever a block has BlockState properties — tells Minecraft
    // this block's state can vary by AGE, so it needs to track it.
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    // Called periodically by the game on a random subset of loaded blocks
    // (this is the exact same system that makes grass spread, crops grow,
    // and leaves decay). Roughly: every loaded chunk gets a handful of random
    // block positions "ticked" a few times per second — NOT every block,
    // every tick, which would be far too expensive.
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);

        if (age < MAX_AGE) {
            // Not fully matured yet — bump the age by one stage.
            // setBlock with flag 2 = update the block state, sync to client,
            // but don't re-trigger neighbor updates unnecessarily.
            level.setBlock(pos, state.setValue(AGE, age + 1), 2);
        } // else {
            // Fully matured — convert into the restored block.
            // This is where ModBlocks.RESTORED needs to exist (see the
            // registration snippet).

            // Folowing changed temporarrily
            // level.setBlock(pos, ModBlocks.RESTORED.defaultBlockState(), 3);
            // level.setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
        // }
        // removed because we dont have a final stage. It never gets back to normal. It just dies. Artificial.
    }

    // Optional but worth having: without this, randomTick would still run,
    // but this explicitly tells the game "yes, this block wants random ticks"
    // in case any other system checks isRandomlyTicking() rather than relying
    // on the properties flag alone. Safe to include for clarity.
    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) <= MAX_AGE;
    }
}