package net.team_capes.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.team_capes.client.TeamCapeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractClientPlayerEntity.class, priority = 500)
public class AbstractClientPlayerEntityMixin {
    @Inject(method = "getSkinTextures", at = @At("RETURN"), cancellable = true)
    private void getSkinTexture_TeamCapes(CallbackInfoReturnable<SkinTextures> cir) {
        var texture = cir.getReturnValue();
        var player = (AbstractClientPlayerEntity) (Object) this;
        var team = player.getScoreboardTeam();
        if (team != null && team.getColor().getColorValue() != null) {
            var teamCapeId = TeamCapeManager.getTeamCapeTexture(player.getTeamColorValue());
            var newTexture = new SkinTextures(
                    texture.texture(),
                    texture.textureUrl(),
                    teamCapeId,
                    texture.elytraTexture(),
                    texture.model(),
                    texture.secure()
            );
            cir.setReturnValue(newTexture);
        }
    }
}