package com.sixonethree.durabilityshow.handler;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyHandler {
	public static KeyBinding showHud;
	
	public static void init() {
		showHud = new KeyBinding("key.hudtoggle", Keyboard.KEY_H, "key.categories.durabilityshow");
		ClientRegistry.registerKeyBinding(showHud);
	}
}