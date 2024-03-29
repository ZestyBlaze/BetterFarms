package dev.teamcitrus.betterfarms.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.teamcitrus.betterfarms.BetterFarmsClient;
import dev.teamcitrus.betterfarms.api.util.QualityUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin {
    @Shadow @Final private PoseStack pose;

    @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"))
    private void betterFarms$renderItemDecorations(Font pFont, ItemStack pStack, int pX, int pY, String pText, CallbackInfo ci) {
        if (pStack.isEmpty() || !pStack.hasTag() || QualityUtil.getQuality(pStack) == null) {
            return;
        }
        pose.popPose();
        BetterFarmsClient.renderIcon((GuiGraphics) (Object) this, pFont, pStack, pX, pY);
        pose.pushPose();
    }
}
