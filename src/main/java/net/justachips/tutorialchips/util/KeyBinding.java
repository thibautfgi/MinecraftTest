package net.justachips.tutorialchips.util;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBinding {
    
    public static final String KEY_CATEGORY_TUTORIAL = "key.category.tutorialchips.tutorial";
    public static final String KEY_DRINK_WATER = "   ";
    
    public static final KeyMapping DRINKING_KEY = new KeyMapping(KEY_DRINK_WATER, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_TUTORIAL);


}
