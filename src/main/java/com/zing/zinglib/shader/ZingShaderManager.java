package com.zing.zinglib.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import java.io.IOException;

public class ZingShaderManager {
    private static PostChain activePostChain;
    private static ResourceLocation currentShaderLocation;
    
    // Resource locations matching your shader json paths
    private static final ResourceLocation CLASSIC_SHADER = ResourceLocation.fromNamespaceAndPath("zinglib", "shaders/post/classic.json");
    private static final ResourceLocation SPACE_SHADER = ResourceLocation.fromNamespaceAndPath("zinglib", "shaders/post/space.json");
    private static final ResourceLocation GLITCH_SHADER = ResourceLocation.fromNamespaceAndPath("zinglib", "shaders/post/glitch.json");

    public static void updateShaders(Minecraft mc) {
        if (mc.level == null || mc.player == null) {
            clearShader();
            return;
        }

        ResourceLocation targetShader = null;
        ResourceLocation currentDimension = mc.level.dimension().location();

        // 1. Check Dimension First (Legacy Console / Classic Mode)
        if (currentDimension.getPath().equals("legacy_console_dimension")) {
            targetShader = CLASSIC_SHADER;
        } else if (currentDimension.getPath().equals("space_dimension")) {
            targetShader = SPACE_SHADER;
        }
        
        // 2. Override with active Super Secret Setting / Special Effect if applied
        if (ActiveEffectHolder.getSpecialEffect() != null) {
            targetShader = ActiveEffectHolder.getSpecialEffect().getShaderLocation();
        }

        // Apply shader if it changed
        if (targetShader != currentShaderLocation) {
            loadShader(mc, targetShader);
        }
    }

    private static void loadShader(Minecraft mc, ResourceLocation shaderLocation) {
        clearShader();
        if (shaderLocation == null) return;

        try {
            // Load shader directly into a standalone chain
            activePostChain = new PostChain(mc.getTextureManager(), mc.getResourceManager(), mc.getMainRenderTarget(), shaderLocation);
            activePostChain.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
            currentShaderLocation = shaderLocation;
        } catch (IOException e) {
            System.err.println("ZiNGLib failed to load shader chain: " + shaderLocation + " -> " + e.getMessage());
            currentShaderLocation = null;
        }
    }

    public static void renderActiveShader(float partialTicks) {
        if (activePostChain != null) {
            // Renders your custom shader layer safely over the final frame buffer
            activePostChain.process(partialTicks);
        }
    }

    public static void resize(int width, int height) {
        if (activePostChain != null) {
            activePostChain.resize(width, height);
        }
    }

    public static void clearShader() {
        if (activePostChain != null) {
            activePostChain.close();
            activePostChain = null;
        }
        currentShaderLocation = null;
    }
}