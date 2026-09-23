package com.cooldude12.unnoficialtadc.client;

import com.cooldude12.unnoficialtadc.ModBlockEntities;
import com.cooldude12.unnoficialtadc.ModBlocks;
import com.cooldude12.unnoficialtadc.ModItems;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class UNOFFICIALTADCMCClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRenderers.register(ModBlockEntities.COSTUME_BLOCK_ENTITY, CostumeBlockEntityRenderer::new);
        ColorProviderRegistry.BLOCK.register(
                (state, level, pos, tintIndex) -> {
                    if (level != null && pos != null) {
                        return net.minecraft.client.renderer.BiomeColors.getAverageGrassColor(level, pos);
                    }
                    return 0x7CBD6B; // fallback color if level/pos aren't available (e.g. inventory rendering)
                },
                ModBlocks.COSTUMED_GRASS_BLOCK
        );
        BlockRenderLayerMap.putBlock(ModBlocks.COSTUMED_GRASS_BLOCK, ChunkSectionLayer.CUTOUT);
		ArmorRenderer.register(new CrownArmorRenderer(), ModItems.CROWN);
	}
}
