package com.cooldude12.unnoficialtadc;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.ArmorType;

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

    public static void registerItems() {
        ItemGroupEvents.modifyEntriesEvent(COMBAT_TAB).register(entries -> entries.accept(CROWN));
    }
}
