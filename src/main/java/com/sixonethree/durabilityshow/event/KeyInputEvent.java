package com.sixonethree.durabilityshow.event;

import org.lwjgl.input.Keyboard;

import com.sixonethree.durabilityshow.client.gui.EnumGuiState;
import com.sixonethree.durabilityshow.client.gui.GuiItemDurability;
import com.sixonethree.durabilityshow.handler.KeyHandler;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputEvent {
	@SubscribeEvent public void keyPressed(InputEvent.KeyInputEvent e) {
		if (KeyHandler.showHud.isPressed()) {
			if ((Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
				if (!GuiItemDurability.getRenderCharacter() && GuiItemDurability.getRenderBaubles()) GuiItemDurability.setRenderBaubles(false);
				GuiItemDurability.setRenderChararcter(!GuiItemDurability.getRenderCharacter());
			} else if ((Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) && Loader.isModLoaded("baubles")) {
				if (!GuiItemDurability.getRenderBaubles() && GuiItemDurability.getRenderCharacter()) GuiItemDurability.setRenderChararcter(false);
				GuiItemDurability.setRenderBaubles(!GuiItemDurability.getRenderBaubles());
			} else {
				if (GuiItemDurability.getGuiState() == EnumGuiState.CLOSED || GuiItemDurability.getGuiState() == EnumGuiState.CLOSING) {
					GuiItemDurability.setGuiState(EnumGuiState.OPENING);
					return;
				}
				if (GuiItemDurability.getGuiState() == EnumGuiState.OPEN || GuiItemDurability.getGuiState() == EnumGuiState.OPENING) {
					GuiItemDurability.setGuiState(EnumGuiState.CLOSING);
					return;
				}
			}
		}
	}
}