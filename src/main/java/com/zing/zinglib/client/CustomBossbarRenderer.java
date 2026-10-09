package com.zing.zinglib.client;

import com.zing.zinglib.bossbar.ZingBossbarManager;
import com.zing.zinglib.bossbar.ZingBossbarManager.ActiveBossInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = "zinglib", value = Dist.CLIENT)
public class CustomBossbarRenderer {

    @SubscribeEvent
    public static void onRenderGuiLayer(RenderGuiLayerEvent.Post event) {
        // Only trigger our overlay processing during the default Boss Overlay step
        if (!event.getName().equals(VanillaGuiLayers.BOSS_OVERLAY)) return;

        Map<UUID, ActiveBossInfo> activeBosses = ZingBossbarManager.getActiveBosses();
        if (activeBosses.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();
        GuiGraphics graphics = event.getGuiGraphics();
        
        int screenWidth = event.getMainWindow().getGuiScaledWidth();
        int yOffset = 12; // Start rendering 12 pixels down from top of screen

        for (ActiveBossInfo bossInfo : activeBosses.values()) {
            var style = bossInfo.getStyle();
            
            // X Center calculation for alignment
            int frameWidth = style.getTextureWidth(); 
            int frameX = (screenWidth / 2) - (frameWidth / 2);

            // 1. Draw the Custom Frame Base/Background (UV: 0, 0)
            graphics.blit(style.getTexture(), frameX, yOffset, 0, 0, frameWidth, style.getTextureHeight() / 2);

            // 2. Calculate dynamic scale width for the filling health bar
            int maxBarWidth = style.getBarWidth();
            int currentBarWidth = (int) (maxBarWidth * bossInfo.getHealthPercent());

            // 3. Draw the Filling Health Bar Overlay (UV: 0, TextureHeight / 2)
            // Stored directly underneath the empty frame in your texture file sheets
            int barX = frameX + style.getBarOffsetX();
            int barY = yOffset + style.getBarOffsetY();
            graphics.blit(style.getTexture(), barX, barY, style.getBarOffsetX(), (style.getTextureHeight() / 2) + style.getBarOffsetY(), currentBarWidth, style.getBarHeight());

            // 4. Draw the Boss Name text floating above or nested within the frame
            Component name = bossInfo.getName();
            int textX = (screenWidth / 2) - (mc.font.width(name) / 2);
            int textY = yOffset + 4; // Shift down slightly inside the frame boundaries
            
            graphics.drawString(mc.font, name, textX, textY, 0xFFFFFF, true);

            // Push next stacked boss bar downward so they don't overlap vertically
            yOffset += (style.getTextureHeight() / 2) + 8; 
        }
    }
}