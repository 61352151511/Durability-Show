package com.sixonethree.durabilityshow.client.gui;

import javax.annotation.Nullable;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

/**
 * Created by NovaViper on 4/4/2017.
 */
public class GuiFactory implements IModGuiFactory {
	@Override
	public void initialize(Minecraft minecraftInstance) {
	
	}
	
	@Override
	public boolean hasConfigGui() {
		return true;
	}
	
	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return new ModGuiConfig(parentScreen);
	}
	
	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return ModGuiConfig.class;
	}
	
	@Nullable
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}
	
	@Nullable
	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}
}