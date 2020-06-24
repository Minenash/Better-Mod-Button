package com.minenash.bettermodbutton.mixin;

import com.minenash.bettermodbutton.ModChecker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(value = TitleScreen.class, priority = 0)
public abstract class TitleScreenMixin extends Screen {

  @Inject(method = "init", at = @At("HEAD"))
  private void setCount(CallbackInfo _c) {
    ModChecker.init();
  }

  protected TitleScreenMixin(Text title) { super(title); }
  @Shadow @Final private boolean doBackgroundFade;
  @Shadow private long backgroundFadeStart;

  @Inject(method = "render", at = @At("TAIL"))
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo _c) {
    int y = this.height - 20 - (ModChecker.needsShifting ? 10 : 0);

    this.drawStringWithShadow(matrices, this.textRenderer, "Fabric:", 2 , y, 0xCCCCCC | getFade());
    this.drawStringWithShadow(matrices, this.textRenderer, Integer.toString(ModChecker.count), 40, y, 0xFFFF55 | getFade());
    this.drawStringWithShadow(matrices, this.textRenderer, I18n.translate("bettermodbutton.modsloaded"), 44 + ModChecker.length * 6, y, 0xCCCCCC | getFade());
  }

  private int getFade() {
    if (!this.doBackgroundFade) return 255 << 24;
    return MathHelper.ceil(MathHelper.clamp((Util.getMeasuringTimeMs() - this.backgroundFadeStart) / 1000.0F - 1.0F, 0.0F, 1.0F) * 255.0F) << 24;
  }

  @Inject(method = "areRealmsNotificationsEnabled", at = @At("RETURN"), cancellable = true)
  public void areRealmsNotificationsEnabled(CallbackInfoReturnable<Boolean> callback) {
    callback.setReturnValue(false);
  }

}