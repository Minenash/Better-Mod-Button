package com.minenash.bettermodbutton.mixin;

import com.minenash.bettermodbutton.ModChecker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {

    protected GameMenuScreenMixin(Text title) { super(title); }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo _c) {
        int y = this.height - 10;

        drawStringWithShadow(matrices, this.textRenderer, "Fabric:"    , 2 , y, 0xCCCCCC);
        drawStringWithShadow(matrices, this.textRenderer, ModChecker.count + ""    , 40, y, 0xFFFF55);
        drawStringWithShadow(matrices, this.textRenderer, I18n.translate("bettermodbutton.modsloaded"), 44 + ModChecker.length * 6, y, 0xCCCCCC);
    }

}
