package com.sixonethree.durabilityshow.event;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sixonethree.durabilityshow.handler.ConfigurationHandler;

public class TooltipEvents {
	@SubscribeEvent public void onItemTooltip(ItemTooltipEvent event) {
		if (!event.showAdvancedItemTooltips) {
			if (event.itemStack != null) {
				ItemStack itemStack = event.itemStack;
				if (itemStack.isItemDamaged()) {
					String toolTip = ConfigurationHandler.getTooltipColor() + StatCollector.translateToLocalFormatted("tooltip.durabilitytooltip", (itemStack.getMaxDamage() - itemStack.getItemDamage()), itemStack.getMaxDamage());
					if (!event.toolTip.contains(toolTip)) {
						event.toolTip.add(toolTip);
					}
				}
			}
		}
	}
}