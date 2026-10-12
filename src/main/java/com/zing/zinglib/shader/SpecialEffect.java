package com.zing.zinglib.shader;

import net.minecraft.resources.Identifier;

public enum SpecialEffect {
    // Dimension presets
    CLASSIC("classic"), SPACE("space"), CARTOON("cartoon"),
    // Special Effects
    THREE_D_GLASSES("threed_glasses"), HUE_SHIFT("hue_shift"), MOVIE_PROJECTION("movie"), 
    MIRRORED("mirrored"), KALEIDOSCOPE("kaleidoscope"), TINT("tint"), 
    PIXELATE("pixelate"), MOSAIC("mosaic"), GLITCH("glitch"),
    // Retro Super Secret Settings
    ART("art"), BUMPY("bumpy"), BLOBS2("blobs2"), PENCIL("pencil"), NOTCH("notch"), 
    COLOR_CONVOLVE("color_convolve"), FLIP("flip"), INVERT("invert"), NTSC("ntsc"), 
    PHOSPHOR("phosphor"), SCAN_PINCUSHION("scan_pincushion"), SOBEL("sobel"), BITS("bits"), 
    GREEN("green"), DESATURATE("desaturate"), BLUR("blur"), WOBBLE("wobble"), 
    BLOBS("blobs"), ANTIALIAS("antialias"), CREEPER("creeper"), SPIDER("spider");

    private final Identifier shaderLocation;

    SpecialEffect(String name) {
        this.shaderLocation = Identifier.fromNamespaceAndPath("zinglib", "shaders/post/" + name + ".json");
    }

    public Identifier getShaderLocation() {
        return this.shaderLocation;
    }
}