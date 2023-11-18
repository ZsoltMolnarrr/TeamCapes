package net.team_capes.mixin.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.team_capes.TeamCapesMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {
    private static final Identifier TEAM_CAPE_TEXTURE = new Identifier(TeamCapesMod.NAMESPACE, "textures/team_cape.png");

    @Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
    private void getCapeTexture(CallbackInfoReturnable<Identifier> cir) {
        var player = (AbstractClientPlayerEntity) (Object) this;
        if (player.getScoreboardTeam() != null) {
            cir.setReturnValue(TEAM_CAPE_TEXTURE);
            cir.cancel();
        }
    }
}