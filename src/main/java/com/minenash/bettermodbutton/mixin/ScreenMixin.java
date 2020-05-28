package com.minenash.bettermodbutton.mixin;

import io.github.prospector.modmenu.ModMenu;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Shadow public int height;
    @Shadow public int width;
    @Shadow protected abstract <T extends AbstractButtonWidget> T addButton(T button);
    @Shadow @Final protected List<Element> children;
    @Shadow @Final protected List<AbstractButtonWidget> buttons;

    @Unique private AbstractButtonWidget bugReportButton = null;

    @Inject(at = @At("HEAD"), method = "addButton", cancellable = true)
    public void addButtonInject(AbstractButtonWidget button, CallbackInfoReturnable callback) {

        String message = button.getMessage();

        if ((Object)this instanceof TitleScreen) {

            if (button.y <= (this.height / 4 + 48 + 24 * 3) - 12)
                button.y += 12;
            else if (button.y > (this.height / 4 + 48 + 24 * 3) + 12)
                button.y -= 12;

            if (message.equals(I18n.translate("menu.online")))
                button.setWidth(98);
            else if (message.equals(I18n.translate("modmenu.title") + " " + I18n.translate("modmenu.loaded", ModMenu.getDisplayedModCount()))) {
                button.y -= 24;
                button.x = this.width / 2 + 2;
                button.setWidth(98);
                button.setMessage(I18n.translate("modmenu.title"));
            }
        }
        else if ((Object)this instanceof GameMenuScreen) {

            if (message.equals(I18n.translate("menu.reportBugs"))) {
                bugReportButton = button;
                callback.cancel();
            }
            else if (message.equals(I18n.translate("modmenu.title") + " " + I18n.translate("modmenu.loaded", ModMenu.getDisplayedModCount()))) {
                if (bugReportButton == null) return;
                button.x = bugReportButton.x;
                button.y = bugReportButton.y + 12;
                button.setWidth(bugReportButton.getWidth());
                button.setMessage(I18n.translate("modmenu.title"));
            }
            else {
                button.y += (button.y >= (this.height / 4 - 16 + 24 * 4 - 1) + 12) ? -12 : 12;
            }

        }

    }

}
