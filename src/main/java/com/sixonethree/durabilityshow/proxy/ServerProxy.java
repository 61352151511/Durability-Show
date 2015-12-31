package com.sixonethree.durabilityshow.proxy;

import net.minecraftforge.common.MinecraftForge;

import com.sixonethree.durabilityshow.event.TooltipEvents;

public class ServerProxy {
	public void init() {
		MinecraftForge.EVENT_BUS.register(new TooltipEvents());
	}
}