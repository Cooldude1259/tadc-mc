package com.cooldude12.unnoficialtadc;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.List;
import java.util.function.Function;

public class ModItems {
    private static final ResourceKey<CreativeModeTab> COMBAT_TAB =
            ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace("combat"));

    public static final Item CROWN = register(
            "crown",
            Item::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.CROWN_MATERIAL, ArmorType.HELMET)
    );

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("tadc-mc", name));

        // Create the item instance.
        T item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static final Item BLAIN = register("blain", Item::new, new Item.Properties().rarity(Rarity.EPIC));

    public static final Item TEST_COSTUME = register("test_costume", CostumeItem::new, new Item.Properties().rarity(Rarity.EPIC));

    public static final Item COSTUME_REMOVER = register("costume_remover", CostumeRemoverItem::new, new Item.Properties());


    public static final ResourceKey<CreativeModeTab> TADCMC_RESOURCE_KEY = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(UNOFFICIALTADCMCServer.MOD_ID, "creative_tab")
    );
    public static final CreativeModeTab TADCMC_TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.CROWN))
            .title(Component.translatable("itemGroup.tadc-mc.main"))
            .displayItems((params, output) -> {
                // Add items to the creative tab
                // output.accept(ModItems.SUSPICIOUS_SUBSTANCE);
                // output.accept(ModItems.POISONOUS_APPLE);
                output.accept(ModItems.CROWN);
                output.accept(ModItems.BLAIN);

                // The tab builder also accepts Blocks
                //output.accept(ModBlocks.CONDENSED_OAK_LOG);
                // output.accept(ModBlocks.PRISMARINE_LAMP);
                output.accept(ModBlocks.TRIM_EXTRACTOR);
                output.accept(ModBlocks.BROKEN);

                // And custom ItemStacks
                //ItemStack stack = new ItemStack(Items.SEA_PICKLE);
                //stack.set(DataComponents.ITEM_NAME, Component.literal("Pickle Rick"));
                //stack.set(DataComponents.LORE, new ItemLore(List.of(Component.literal("I'm pickle riiick!!"))));
                //output.accept(stack);
            })
            .build();

    public static final ResourceKey<CreativeModeTab> TADCMC_COSTUMES_RESOURCE_KEY = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(UNOFFICIALTADCMCServer.MOD_ID, "costumes")
    );
    public static final CreativeModeTab TADCMC_COSTUMES_TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.TEST_COSTUME))
            .title(Component.translatable("itemGroup.tadc-mc.costumes"))
            .displayItems((params, output) -> {
                output.accept(ModItems.COSTUME_REMOVER);
                output.accept(ModItems.TEST_COSTUME);
            })
            .build();

    public static void registerItems() {
        // Register the group.
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TADCMC_RESOURCE_KEY, TADCMC_TAB);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TADCMC_COSTUMES_RESOURCE_KEY, TADCMC_COSTUMES_TAB);
        // ItemGroupEvents.modifyEntriesEvent(COMBAT_TAB).register(entries -> entries.accept(CROWN));
    }
}
