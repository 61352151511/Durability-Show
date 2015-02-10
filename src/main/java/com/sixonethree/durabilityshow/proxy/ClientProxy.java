package com.sixonethree.durabilityshow.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import com.sixonethree.durabilityshow.client.gui.GuiItemDurability;

public class ClientProxy extends CommonProxy {
	@Override public void init() {
		super.init();
		MinecraftForge.EVENT_BUS.register(new GuiItemDurability(Minecraft.getMinecraft()));
	}
}