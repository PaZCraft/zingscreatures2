package com.zing.zinglib.bossbar;

import net.minecraft.resources.Identifier;

public class BossbarStyle {
    private final Identifier texture;
    private final int textureWidth;
    private final int textureHeight;
    private final int barOffsetX;
    private final int barOffsetY;
    private final int barWidth;
    private final int barHeight;

    public BossbarStyle(Identifier texture, int textureWidth, int textureHeight, 
                        int barOffsetX, int barOffsetY, int barWidth, int barHeight) {
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.barOffsetX = barOffsetX;
        this.barOffsetY = barOffsetY;
        this.barWidth = barWidth;
        this.barHeight = barHeight;
    }

    public Identifier getTexture() { return texture; }
    public int getTextureWidth() { return textureWidth; }
    public int getTextureHeight() { return textureHeight; }
    public int getBarOffsetX() { return barOffsetX; }
    public int getBarOffsetY() { return barOffsetY; }
    public int getBarWidth() { return barWidth; }
    public int getBarHeight() { return barHeight; }
}