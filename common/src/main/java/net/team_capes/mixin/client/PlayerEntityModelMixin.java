package net.team_capes.mixin.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.team_capes.client.CapeRenderContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public class PlayerEntityModelMixin {
    @Shadow @Final private ModelPart cloak;

    @Inject(method = "renderCape", at = @At("HEAD"), cancellable = true)
    private void renderCape_HEAD_TeamCapes(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, CallbackInfo ci) {
        if (CapeRenderContext.color != null) {
            cloak.render(matrices, vertices, light, overlay, CapeRenderContext.color.red(), CapeRenderContext.color.green(), CapeRenderContext.color.blue(), 1.0F);
            ci.cancel();
        }
    }
}
