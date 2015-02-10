package com.sixonethree.durabilityshow.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.sixonethree.durabilityshow.client.gui.EnumGuiState;
import com.sixonethree.durabilityshow.client.gui.GuiItemDurability;

public class OnTickEvent {
	@SubscribeEvent public void onRenderTick(TickEvent.RenderTickEvent event) {
		if (GuiItemDurability.getGuiState() == EnumGuiState.CLOSING) {
			GuiItemDurability.raiseOffset();
			if (GuiItemDurability.getOffset() >= GuiItemDurability.getCloseSize()) GuiItemDurability.setGuiState(EnumGuiState.CLOSED);
		}
		if (GuiItemDurability.getGuiState() == EnumGuiState.CLOSED) {
			if (GuiItemDurability.getOffset() < GuiItemDurability.getCloseSize()) GuiItemDurability.setGuiState(EnumGuiState.CLOSING);
		}
		if (GuiItemDurability.getGuiState() == EnumGuiState.OPENING) {
			GuiItemDurability.lowerOffset();
			if (GuiItemDurability.getOffset() <= 0) GuiItemDurability.setGuiState(EnumGuiState.OPEN);
		}
	}
}