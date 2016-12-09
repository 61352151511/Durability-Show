package com.sixonethree.durabilityshow.handler;

import java.io.File;

import com.sixonethree.durabilityshow.client.gui.EnumCorner;
import com.sixonethree.durabilityshow.client.gui.GuiItemDurability;
import com.sixonethree.durabilityshow.reference.Reference;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
	public static Configuration configuration;
	private static TextFormatting tooltipColor = TextFormatting.GRAY;
	
	public static void init(File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	@SubscribeEvent public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		tooltipColor = TextFormatting.fromColorIndex(configuration.getInt("Durability Tooltip Color", Configuration.CATEGORY_GENERAL, 7, 0, 15,
		"0 - Black\n1 - Dark Blue\n2 - Dark Green\n3 - Dark Aqua\n4 - Dark Red\n5 - Dark Purple\n6 - Gold\n7 - Gray\n8 - Dark Gray\n9 - Blue\n10 - Green\n11 - Aqua\n12 - Red\n13 - Light Purple\n14 - Yellow\n15 - White"));
		GuiItemDurability.setCorner(EnumCorner.values()[configuration.getInt("Position", Configuration.CATEGORY_GENERAL, 0, 0, 3,
		"0 - Bottom Right\n1 - Bottom Left\n2 - Top Right (Will conflict with status effects)\n3 - Top Left")]);
		if (configuration.hasChanged()) {
			configuration.save();
		}
	}
	
	public static TextFormatting getTooltipColor() { return tooltipColor; }
}