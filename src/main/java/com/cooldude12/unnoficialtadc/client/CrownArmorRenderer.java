package com.cooldude12.unnoficialtadc.client;

import com.mojang.blaze3d.vertex.PoseStack;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * Draws the crown's item model on the wearer's head.
 *
 * <p>Vanilla renders worn armor as a flat texture painted onto the humanoid model, which can't
 * represent the crown's geometry. Registering an {@link ArmorRenderer} replaces that for this item
 * so the Blockbench model is used instead.
 */
public class CrownArmorRenderer implements ArmorRenderer {
	@Override
	public void render(PoseStack matrices, SubmitNodeCollector queue, ItemStack stack, HumanoidRenderState state,
			EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
		if (slot != EquipmentSlot.HEAD) {
			return;
		}

		// LivingEntityRenderer skips resolving state.headItem for stacks that have an equipment
		// asset (HumanoidArmorLayer.shouldRender), so the crown's model has to be resolved here.
		// A fresh state per call is required: submit() only records draw commands, so a shared
		// instance would be overwritten by the next wearer before the frame is drawn.
		ItemStackRenderState itemState = new ItemStackRenderState();
		Minecraft.getInstance().getItemModelResolver()
				.updateForTopItem(itemState, stack, ItemDisplayContext.HEAD, Minecraft.getInstance().level, null, 0);

		if (itemState.isEmpty()) {
			return;
		}

		matrices.pushPose();
		// Follow the head bone, then apply the same framing vanilla uses for head-slot items so
		// the model's "head" display transform lands where Blockbench previewed it.
		contextModel.head.translateAndRotate(matrices);
		CustomHeadLayer.translateToHead(matrices, CustomHeadLayer.Transforms.DEFAULT);
		itemState.submit(matrices, queue, light, OverlayTexture.NO_OVERLAY, 0);
		matrices.popPose();
	}

	@Override
	public boolean shouldRenderDefaultHeadItem(net.minecraft.world.entity.LivingEntity entity, ItemStack stack) {
		return false;
	}
}
