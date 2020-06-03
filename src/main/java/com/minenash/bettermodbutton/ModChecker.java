package com.minenash.bettermodbutton;

import io.github.prospector.modmenu.ModMenu;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

public class ModChecker {
    public static int count, length = 0;
    public static boolean needsShifting;

    public static void init() {
        count = ModMenu.ROOT_NONLIB_MODS.size();
        length = 0;
        for(int m = count; m != 0; m/=10){ ++length; }

        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            if (mod.getMetadata().getId().equals("optifabric"))
                needsShifting = true;
        }
    }
}
