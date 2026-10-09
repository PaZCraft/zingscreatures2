package com.zing.zinglib.mixin;

import com.zing.zinglib.shader.ZingShaderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class ShaderInjectionMixin {

    @Shadow @Final Minecraft minecraft;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MainWindow;refreshFormageSize()V", shift = At.Shift.BEFORE))
    private void injectZingShaders(float partialTicks, long nanoTime, boolean renderLevel, CallbackInfo ci) {
        // Updates state and handles automatic dimension scanning
        ZingShaderManager.updateShaders(this.minecraft);
        
        // Forces execution at the absolute end of the frame rendering loop
        ZingShaderManager.renderActiveShader(partialTicks);
    }
    
    @Inject(method = "resize", at = @At("HEAD"))
    private void onResize(int width, int height, CallbackInfo ci) {
        ZingShaderManager.resize(width, height);
    }
}