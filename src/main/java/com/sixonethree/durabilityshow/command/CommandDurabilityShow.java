package com.sixonethree.durabilityshow.command;

import java.util.ArrayList;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import com.sixonethree.durabilityshow.client.gui.EnumCorner;
import com.sixonethree.durabilityshow.client.gui.GuiItemDurability;

public class CommandDurabilityShow extends CommandBase {
	@SuppressWarnings("serial") private static ArrayList<String> acceptable = new ArrayList<String>() {
		{
			add("tl");
			add("tr");
			add("bl");
			add("br");
		}
	};
	
	@Override public String getCommandName() { return "durabilityshow"; }
	@Override public String getCommandUsage(ICommandSender sender) { return "/durabilityshow <tr,tl,br,bl>"; }
	@Override public int getRequiredPermissionLevel() { return 0; }
	@Override public boolean canCommandSenderUseCommand(ICommandSender sender) { return sender instanceof EntityPlayer; }
	
	@Override public void processCommand(ICommandSender sender, String[] args) {
		if (args.length != 1) {
			sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
			return;
		} else {
			if (!acceptable.contains(args[0])) {
				sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
				return;
			} else {
				if (args[0].trim().equalsIgnoreCase("tl")) GuiItemDurability.setCorner(EnumCorner.TOP_LEFT);
				if (args[0].trim().equalsIgnoreCase("tr")) GuiItemDurability.setCorner(EnumCorner.TOP_RIGHT);
				if (args[0].trim().equalsIgnoreCase("bl")) GuiItemDurability.setCorner(EnumCorner.BOTTOM_LEFT);
				if (args[0].trim().equalsIgnoreCase("br")) GuiItemDurability.setCorner(EnumCorner.BOTTOM_RIGHT);
			}
		}
	}
}