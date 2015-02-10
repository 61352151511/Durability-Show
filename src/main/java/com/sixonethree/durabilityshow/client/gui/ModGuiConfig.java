package com.sixonethree.durabilityshow.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

import com.sixonethree.durabilityshow.handler.ConfigurationHandler;
import com.sixonethree.durabilityshow.reference.Reference;

public class ModGuiConfig extends GuiConfig {
	public ModGuiConfig(GuiScreen guiScreen) {
		super(guiScreen, new ConfigElement(ConfigurationHandler.configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
	}
}