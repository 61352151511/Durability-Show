package com.sixonethree.durabilityshow.event;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sixonethree.durabilityshow.handler.ConfigurationHandler;

public class TooltipEvents {
	@SubscribeEvent public void onItemTooltip(ItemTooltipEvent event) {
		if (!event.isShowAdvancedItemTooltips()) {
			if (event.getItemStack() != null) {
				ItemStack itemStack = event.getItemStack();
				if (itemStack.isItemDamaged()) {
					String toolTip = ConfigurationHandler.getTooltipColor() + I18n.translateToLocalFormatted("tooltip.durabilitytooltip", (itemStack.getMaxDamage() - itemStack.getItemDamage()), itemStack.getMaxDamage());
					if (!event.getToolTip().contains(toolTip)) {
						event.getToolTip().add(toolTip);
					}
				}
			}
		}
	}
}