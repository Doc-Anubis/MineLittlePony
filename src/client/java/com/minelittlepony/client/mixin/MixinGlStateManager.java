package com.minelittlepony.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.minelittlepony.client.render.LevitatingItemRenderer;
import com.minelittlepony.client.render.tileentities.skull.PonySkullRenderer;

import com.mojang.blaze3d.platform.GlStateManager;

@Mixin(GlStateManager.class)
public abstract class MixinGlStateManager {

    @Inject(method = "setProfile("
            + "Lcom/mojang/blaze3d/platform/GlStateManager$RenderMode;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false)
    private static void enableBlendProfile(GlStateManager.RenderMode profile, CallbackInfo info) {
        if (profile == GlStateManager.RenderMode.PLAYER_SKIN && PonySkullRenderer.ponyInstance.usesTransparency()) {
            LevitatingItemRenderer.enableItemGlowRenderProfile();
            info.cancel();
        }
    }
}
