package com.sixonethree.durabilityshow;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import com.sixonethree.durabilityshow.command.CommandDurabilityShow;
import com.sixonethree.durabilityshow.event.KeyInputEvent;
import com.sixonethree.durabilityshow.event.OnTickEvent;
import com.sixonethree.durabilityshow.handler.ConfigurationHandler;
import com.sixonethree.durabilityshow.handler.KeyHandler;
import com.sixonethree.durabilityshow.proxy.IProxy;
import com.sixonethree.durabilityshow.reference.Reference;
import com.sixonethree.durabilityshow.utility.LogHelper;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS) public class DurabilityShow {
	@Mod.Instance(Reference.MOD_ID) public static DurabilityShow instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY) public static IProxy proxy;
	
	@Mod.EventHandler public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		FMLCommonHandler.instance().bus().register(new KeyInputEvent());
		FMLCommonHandler.instance().bus().register(new OnTickEvent());
		
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
		event.registerServerCommand(new CommandDurabilityShow());
	}
}
