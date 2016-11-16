package com.sixonethree.durabilityshow.event;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sixonethree.durabilityshow.handler.ConfigurationHandler;

public class TooltipEvents {
	@SubscribeEvent public void onItemTooltip(ItemTooltipEvent event) {
		if (!event.isShowAdvancedItemTooltips()) {
			if (!event.getItemStack().func_190926_b()) {
				ItemStack itemStack = event.getItemStack();
				if (itemStack.isItemDamaged()) {
					String toolTip = ConfigurationHandler.getTooltipColor() + I18n.format("tooltip.durabilitytooltip", (itemStack.getMaxDamage() - itemStack.getItemDamage()), itemStack.getMaxDamage());
					if (!event.getToolTip().contains(toolTip)) {
						event.getToolTip().add(toolTip);
					}
				}
			}
		}
	}
}