package com.sixonethree.durabilityshow.handler;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeyHandler {
	@SideOnly(Side.CLIENT) public static KeyBinding showHud;
	public static void init() {
		showHud = new KeyBinding("key.hudtoggle", Keyboard.KEY_H, "key.categories.durabilityshow");
		ClientRegistry.registerKeyBinding(showHud);
	}
}