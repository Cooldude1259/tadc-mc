package com.cooldude12.unnoficialtadc;

import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class ModArmorMaterials {
    // 1. Key for the equipment asset at assets/tadc-mc/equipment/crown.json
    public static final ResourceKey<EquipmentAsset> CROWN_ASSET =
            ResourceKey.create(EquipmentAssets.ROOT_ID, UNOFFICIALTADCMCServer.id("crown"));

    // 2. The armor material record itself
    public static final ArmorMaterial CROWN_MATERIAL = new ArmorMaterial(
            // Base durability multiplier
            15,
            // Defense per slot: only the helmet gets armor points
            Map.of(ArmorType.HELMET, 3),
            // Enchantability
            15,
            // Equip sound
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            // Toughness
            2.0F,
            // Knockback resistance
            0.0F,
            // Repair ingredient tag
            ItemTags.REPAIRS_DIAMOND_ARMOR,
            // Equipment asset that supplies the render layers
            CROWN_ASSET
    );
}
