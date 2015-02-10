package com.sixonethree.durabilityshow.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import com.sixonethree.durabilityshow.handler.ConfigurationHandler;
import com.sixonethree.durabilityshow.reference.Reference;

import cpw.mods.fml.client.config.GuiConfig;

public class ModGuiConfig extends GuiConfig {
	@SuppressWarnings({"rawtypes", "unchecked"}) public ModGuiConfig(GuiScreen guiScreen) {
		super(guiScreen, new ConfigElement(ConfigurationHandler.configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
	}
}