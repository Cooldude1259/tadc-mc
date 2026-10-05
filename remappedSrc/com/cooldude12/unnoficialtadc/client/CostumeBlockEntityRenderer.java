package com.cooldude12.unnoficialtadc.client;

import com.cooldude12.unnoficialtadc.CostumeBlockEntity;
import com.cooldude12.unnoficialtadc.UNOFFICIALTADCMCServer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CostumeBlockEntityRenderer implements BlockEntityRenderer<CostumeBlockEntity, CostumeBlockEntityRenderState> {

    private final ItemModelResolver itemModelResolver;

    public CostumeBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public CostumeBlockEntityRenderState createRenderState() {
        return new CostumeBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(CostumeBlockEntity blockEntity, CostumeBlockEntityRenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);

        itemModelResolver.updateForTopItem(
                state.itemRenderState,
                blockEntity.getCostume(),
                ItemDisplayContext.FIXED,
                blockEntity.getLevel(),
                (net.minecraft.world.entity.ItemOwner) null,
                0
        );
    }

    @Override
    public void submit(CostumeBlockEntityRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
        if (state.itemRenderState.isEmpty()) {
            return;
        }

        matrices.pushPose();
        matrices.translate(0.5, 0.5, 0.5); // center in the block space
        state.itemRenderState.submit(matrices, queue, 15728880, OverlayTexture.NO_OVERLAY, 0);
        matrices.popPose();
    }
}