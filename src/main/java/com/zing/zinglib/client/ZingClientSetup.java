package com.zing.zinglib.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent; // Or equivalent lifecycle event
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = "zinglib", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ZingClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Force your block instances to process transparent layers (cutout) 
            // instead of solid rendering maps
            // Example mappings if your blocks are registered under custom instances:
            // ItemBlockRenderTypes.setRenderLayer(MyBlocks.CLAY_VASE.get(), RenderType.cutout());
        });
    }
}