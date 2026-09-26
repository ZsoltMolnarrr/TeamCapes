package net.team_capes.config;

public class ClientConfig {
    /**
     * Pads Iris's ENTITY vertex format to a multiple of 4 bytes, so vanilla's translucency sort
     * (which reads the vertex buffer as floats) doesn't read positions from drifting offsets.
     * Stopgap until the upstream Iris fix lands; the padding is skipped if Iris already aligns it.
     * Takes effect on game restart.
     */
    public boolean iris_entity_format_alignment_fix = false;
}
