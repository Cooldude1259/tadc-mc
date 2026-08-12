package com.cooldude12.unnoficialtadc;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import java.util.function.Function;

public class ModBlocks {
    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
        // Create a registry key for the block
        ResourceKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.setId(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            ResourceKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static ResourceKey<Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("tadc-mc", name));
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("tadc-mc", name));
    }

    public static void initialize() {}

    public static final Block BROKEN = register(
            "broken",
            Block::new,
            BlockBehaviour.Properties.of().strength(1.0f).noOcclusion(),
            true
    );

    // Maybe delete?

    // public static final Block RESTORED = register(
    //        "restored",
    //        Block::new,
    //        BlockBehaviour.Properties.of().strength(1.5f),
    //        true
    //);

    /*public static final Block SHELL = register(
            "shell",
            ShellBlock::new,
            BlockBehaviour.Properties.of().strength(1.0f).randomTicks(),
            true
    );

    public static final Block MIND = register(
            "mind",
            Block::new,
            BlockBehaviour.Properties.of().strength(1.5f).lightLevel(state -> 15),
            true
    );

    public static final Block MIND_DEEP = register(
            "mind_deep",
            Block::new,
            BlockBehaviour.Properties.of().strength(1.5f).lightLevel(state -> 7),
            true
    );*/

}