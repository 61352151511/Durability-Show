package com.sixonethree.durabilityshow.proxy;

import net.minecraftforge.common.MinecraftForge;

import com.sixonethree.durabilityshow.event.TooltipEvents;

public abstract class CommonProxy implements IProxy {
	public void init() {
		MinecraftForge.EVENT_BUS.register(new TooltipEvents());
		MinecraftForge.EVENT_BUS.register(this);
	}
}