package com.sixonethree.durabilityshow.event;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TooltipEvents {
	@SubscribeEvent public void onItemTooltip(ItemTooltipEvent event) {
		if (!event.showAdvancedItemTooltips) {
			if (event.itemStack != null) {
				ItemStack is = event.itemStack;
				if (is.isItemDamaged()) {
					String ToolTip = EnumChatFormatting.GRAY + "Durability: " + (is.getMaxDurability() - is.getCurrentDurability() + " / " + is.getMaxDurability());
					if (!event.toolTip.contains(ToolTip)) {
						event.toolTip.add(ToolTip);
					}
				}
			}
		}
	}
}