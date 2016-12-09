package com.sixonethree.durabilityshow;

import com.sixonethree.durabilityshow.event.KeyInputEvent;
import com.sixonethree.durabilityshow.event.OnTickEvent;
import com.sixonethree.durabilityshow.handler.ConfigurationHandler;
import com.sixonethree.durabilityshow.handler.KeyHandler;
import com.sixonethree.durabilityshow.proxy.ServerProxy;
import com.sixonethree.durabilityshow.reference.Reference;
import com.sixonethree.durabilityshow.utility.LogHelper;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, clientSideOnly = true, acceptedMinecraftVersions = Reference.ACCEPTED_MINECRAFT_VERSIONS, guiFactory = Reference.GUI_FACTORY_CLASS, dependencies = Reference.DEPENDENCIES) public class DurabilityShow {
	@Mod.Instance(Reference.MOD_ID) public static DurabilityShow instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY) public static ServerProxy proxy;
	
	@Mod.EventHandler public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
		MinecraftForge.EVENT_BUS.register(new KeyInputEvent());
		MinecraftForge.EVENT_BUS.register(new OnTickEvent());
		
		LogHelper.info("Pre-Init Complete");
	}
	
	@Mod.EventHandler public void init(FMLInitializationEvent event) {
		KeyHandler.init();
		proxy.init();
		LogHelper.info("Init Complete");
	}
	
	@Mod.EventHandler public void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Post-Init Complete");
	}
	
	@Mod.EventHandler public void serverStarting(FMLServerStartingEvent event) {
		proxy.init();
	}
}
