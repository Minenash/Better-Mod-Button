package com.minenash.bettermodbutton.mixin;

import com.minenash.bettermodbutton.ModCount;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
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
    public void render(int mouseX, int mouseY, float delta, CallbackInfo _) {
        int y = this.height - 10;
        this.drawString(this.font, "Fabric:"    , 2 , y, 0xCCCCCC);
        this.drawString(this.font, ModCount.count + ""    , 40, y, 0xFFFF55);
        this.drawString(this.font, I18n.translate("bettermodbutton.modsloaded"), 44 + ModCount.length * 6, y, 0xCCCCCC);
    }

}
