package com.minenash.bettermodbutton.mixin;

import com.minenash.bettermodbutton.ModCount;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = TitleScreen.class, priority = 0)
public abstract class TitleScreenMixin extends Screen {

  @Inject(method = "init", at = @At("HEAD"))
  private void setCount(CallbackInfo _) {
    ModCount.init();
  }

  protected TitleScreenMixin(Text title) { super(title); }
  @Shadow @Final private boolean doBackgroundFade;
  @Shadow private long backgroundFadeStart;

  @Inject(method = "render", at = @At("TAIL"))
  public void render(int mouseX, int mouseY, float delta, CallbackInfo _) {
    int y = this.height - 20;
    this.drawString(this.font, "Fabric:", 2 , y, 0xCCCCCC | getFade());
    this.drawString(this.font, Integer.toString(ModCount.count), 40, y, 0xFFFF55 | getFade());
    this.drawString(this.font, I18n.translate("bettermodbutton.modsloaded"), 44 + ModCount.length * 6, y, 0xCCCCCC | getFade());
  }

  private int getFade() {
    if (!this.doBackgroundFade) return 255 << 24;
    return MathHelper.ceil(MathHelper.clamp((Util.getMeasuringTimeMs() - this.backgroundFadeStart) / 1000.0F - 1.0F, 0.0F, 1.0F) * 255.0F) << 24;
  }

}