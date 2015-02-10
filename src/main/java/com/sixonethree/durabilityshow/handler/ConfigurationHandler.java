package com.sixonethree.durabilityshow.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.sixonethree.durabilityshow.client.gui.EnumCorner;
import com.sixonethree.durabilityshow.client.gui.GuiItemDurability;
import com.sixonethree.durabilityshow.reference.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
	public static Configuration configuration;
	
	public static void init(File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	@SubscribeEvent public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		GuiItemDurability.setCorner(EnumCorner.values()[configuration.getInt("Position", Configuration.CATEGORY_GENERAL, 0, 0, 3, "0 Bottom Right, 1 Bottom Left, 2 Top Right, 3 Top Left")]);
		if (configuration.hasChanged()) {
			configuration.save();
		}
	}
}