package net.team_capes.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.*;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.team_capes.TeamCapesMod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeamCapeManager {
    private static final Identifier GRAYSCALE = new Identifier(TeamCapesMod.NAMESPACE, "textures/team_cape.png");
    private static final Map<Integer, Identifier> TEAM_CAPE_TEXTURES = new HashMap<>();
    public static Identifier getTeamCapeTexture(int colorDecimal) {
        if (TEAM_CAPE_TEXTURES.containsKey(colorDecimal)) {
            return TEAM_CAPE_TEXTURES.get(colorDecimal);
        } else {
            var grayScaleResource = new AccessibleResourceTexture(GRAYSCALE);
            var textureId = new Identifier(TeamCapesMod.NAMESPACE, "textures/team_cape_" + colorDecimal);
            try {
                var image = grayScaleResource.loadImage(MinecraftClient.getInstance().getResourceManager());
                var color = Color.from(colorDecimal);

                var coloredImage = colorizeImage(image, color);
                var texture = new NativeImageBackedTexture(coloredImage);
                System.out.println("Registering texture: " + textureId + " | texture:" + texture);
                textureManager().registerTexture(textureId, texture);
                TEAM_CAPE_TEXTURES.put(colorDecimal, textureId);
            } catch (Exception e) {
                System.err.println("Failed to load team cape texture: " + textureId + " | error" + e.getMessage());
            }

            return textureId;
        }
    }

    private static TextureManager textureManager() {
        return MinecraftClient.getInstance().getTextureManager();
    }

    private static NativeImage colorizeImage(NativeImage grayScaleImage, Color color) {
        // var pixels = grayScaleImage.copyPixelsRgba();
        var coloredImage = new NativeImage(grayScaleImage.getWidth(), grayScaleImage.getHeight(), true);
        for (int y = 0; y < grayScaleImage.getHeight(); y++) {
            for (int x = 0; x < grayScaleImage.getWidth(); x++) {
                var pixel = grayScaleImage.getColor(x, y);
                // int pixel = pixels[x + (y * grayScaleImage.getWidth())];
//                int originalRed = (pixel) & 0xFF;
//                int originalGreen = (pixel >> 8) & 0xFF;
//                int originalBlue = (pixel >> 16) & 0xFF;

                // Format is ABGR
                int alpha = (pixel >> 24) & 0xFF;
                int blue = (int) (((pixel >> 16) & 0xFF) * color.blue());
                int green = (int) (((pixel >> 8) & 0xFF) * color.green());
                int red = (int) (((pixel) & 0xFF) * color.red());
                int newPixel = (alpha << 24) | (blue << 16) | (green << 8) | red;
                coloredImage.setColor(x, y, newPixel);
            }
        }
        return coloredImage;
    }

    private static class AccessibleResourceTexture extends ResourceTexture {
        public AccessibleResourceTexture(Identifier identifier) {
            super(identifier);
        }

        public NativeImage loadImage(ResourceManager resourceManager) throws IOException {
            return loadTextureData(resourceManager).getImage();
        }
    }
}
