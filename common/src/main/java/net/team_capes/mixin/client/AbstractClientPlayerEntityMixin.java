package net.team_capes.mixin.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.team_capes.TeamCapesMod;
import net.team_capes.client.TeamCapeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractClientPlayerEntity.class, priority = 500)
public class AbstractClientPlayerEntityMixin {
    @Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
    private void getCapeTexture(CallbackInfoReturnable<Identifier> cir) {
        var player = (AbstractClientPlayerEntity) (Object) this;
        var team = player.getScoreboardTeam();
        if (team != null && team.getColor().getColorValue() != null) {
            cir.setReturnValue(TeamCapeManager.getTeamCapeTexture(player.getTeamColorValue()));
            cir.cancel();
        }
    }
}