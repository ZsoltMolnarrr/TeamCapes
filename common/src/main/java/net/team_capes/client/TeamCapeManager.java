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
                throw new RuntimeException(e);
            }


            // Colorize grayscale image


            return textureId;
        }
    }

    private static TextureManager textureManager() {
        return MinecraftClient.getInstance().getTextureManager();
    }

    private static NativeImage colorizeImage(NativeImage grayScaleImage, Color color) {
        var coloredImage = new NativeImage(grayScaleImage.getWidth(), grayScaleImage.getHeight(), true);

        for (int y = 0; y < grayScaleImage.getHeight(); y++) {
            for (int x = 0; x < grayScaleImage.getWidth(); x++) {
                int alpha = grayScaleImage.getOpacity(x, y);
                int red = (int) (grayScaleImage.getRed(x, y) * color.red());
                int green = (int) (grayScaleImage.getGreen(x, y) * color.green());
                int blue = (int) (grayScaleImage.getBlue(x, y) * color.blue());

                System.out.println("Pixel: " + x + ", " + y + " | " + red + ", " + green + ", " + blue + ", " + alpha);

                int newPixel = (red << 24) | (green << 16) | (blue << 8) | alpha;
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
