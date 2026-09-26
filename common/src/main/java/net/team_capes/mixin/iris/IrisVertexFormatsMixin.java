package net.team_capes.mixin.iris;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.VertexFormat;
import net.team_capes.TeamCapesMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * BuiltBuffer.collectCentroids indexes the vertex buffer in floats (vertexSize / 4), so it only
 * works on a stride that's a multiple of 4. Iris's ENTITY format ends on at_tangent at 54 bytes,
 * so the translucency sort reads positions from drifting offsets. Mirrors the pending Iris fix
 * (`.padding(2)` before ENTITY's `build()`). Opt-in, and allowed to fail (non-required config,
 * require = 0); no-op once Iris aligns the format itself.
 */
@Mixin(targets = "net.irisshaders.iris.vertices.IrisVertexFormats")
public class IrisVertexFormatsMixin {
    @WrapOperation(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexFormat$Builder;build()Lnet/minecraft/client/render/VertexFormat;", ordinal = 1),
            require = 0
    )
    private static VertexFormat padEntityFormat_TeamCapes(VertexFormat.Builder builder, Operation<VertexFormat> original) {
        var format = original.call(builder);
        int remainder = format.getVertexSizeByte() % 4;
        if (remainder == 0 || !TeamCapesMod.config().iris_entity_format_alignment_fix) {
            return format;
        }
        return original.call(builder.skip(4 - remainder));
    }
}
