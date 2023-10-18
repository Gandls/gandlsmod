package net.nathan.gandlsmod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_TUTORIAL = "key.category.gandlsmod.gandls";
    public static final String KEY_DRINK_WATER = "key.gandlsmod.drink_water";
    public static final String KEY_ABILITY_2 = "key.gandlsmod.ability_2";

    public static final KeyMapping DRINKING_KEY = new KeyMapping(KEY_DRINK_WATER, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, KEY_CATEGORY_TUTORIAL);
    public static final KeyMapping ABILITY_2 = new KeyMapping(KEY_ABILITY_2,KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X,KEY_CATEGORY_TUTORIAL);
}
