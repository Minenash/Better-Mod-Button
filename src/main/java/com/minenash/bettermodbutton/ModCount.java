package com.minenash.bettermodbutton;

import io.github.prospector.modmenu.ModMenu;

public class ModCount {
    public static int count, length = 0;

    public static void init() {
        count = ModMenu.ROOT_NONLIB_MODS.size();
        length = 0;
        for(int m = count; m != 0; m/=10){ ++length; }
    }
}
