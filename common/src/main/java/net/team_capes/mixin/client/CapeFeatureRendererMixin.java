package net.team_capes.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.team_capes.client.CapeRenderContext;
import net.team_capes.client.Color;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CapeFeatureRenderer.class)
public class CapeFeatureRendererMixin {

    @WrapOperation(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/PlayerEntityModel;renderCape(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V")
    )
    private void renderCape_Wrapped(
            // Mixin Parameters
            PlayerEntityModel instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, Operation<Void> original,
            // Context Parameters
            MatrixStack arg, VertexConsumerProvider arg2, int i, AbstractClientPlayerEntity player, float f, float g, float h, float j, float k, float l
    ) {
        if (player.getScoreboardTeam() != null) {
            CapeRenderContext.color = Color.from(player.getTeamColorValue());
            original.call(instance, matrices, vertices, light, overlay);
            CapeRenderContext.color = Color.WHITE;
        } else {
            original.call(instance, matrices, vertices, light, overlay);
        }
    }
}
