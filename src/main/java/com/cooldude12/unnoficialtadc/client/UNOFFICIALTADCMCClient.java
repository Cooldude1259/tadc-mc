package com.cooldude12.unnoficialtadc.client;

import com.cooldude12.unnoficialtadc.ModItems;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;

public class UNOFFICIALTADCMCClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ArmorRenderer.register(new CrownArmorRenderer(), ModItems.CROWN);
	}
}
