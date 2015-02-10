package com.sixonethree.durabilityshow.client.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiItemDurability extends Gui {
	private static Minecraft minecraftInstance;
	private static EnumGuiState guiState = EnumGuiState.OPEN;
	private static EnumCorner corner = EnumCorner.BOTTOM_RIGHT;
	private static int offsetPosition = 0;
	private static int closeSize = 16;
	private static int color_white = Color.WHITE.getRGB();
	private static FontRenderer fontRenderer;
	private static RenderItem itemRender = RenderItem.getInstance();
	
	private static final int BOOTS = 1;
	private static final int LEGGINGS = 2;
	private static final int CHESTPLATE = 3;
	private static final int HELMET = 4;
	
	public static EnumGuiState getGuiState() { return guiState; }
	public static int getOffset() { return offsetPosition; }
	public static void setGuiState(EnumGuiState State) { guiState = State; }
	public static EnumCorner getCorner() { return corner; }
	public static void setCorner(EnumCorner newCorner) { corner = newCorner; }
	public static void lowerOffset() { offsetPosition --; }
	public static void raiseOffset() { offsetPosition ++; }
	public static void setCloseSize(int size) { closeSize = size; }
	public static int getCloseSize() { return closeSize; }
	
	public GuiItemDurability(Minecraft MC) {
		super();
		minecraftInstance = MC;
		fontRenderer = MC.fontRendererObj;
	}
	
	private int getArrowsInInventory() {
		int Arrows = 0;
		for (ItemStack stack : minecraftInstance.thePlayer.inventory.mainInventory) {
			if (stack != null) {
				if (stack.getItem() == Items.arrow) {
					Arrows += stack.stackSize;
				}
			}
		}
		return Arrows;
	}
	
	public boolean allNull(ItemStack... stacks) {
		for (ItemStack s : stacks) {
			if (s != null) return false;
		}
		return true;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL) public void onRender(RenderGameOverlayEvent.Post event) {
		InventoryPlayer Inventory = minecraftInstance.thePlayer.inventory;
		ItemStack current = Inventory.getCurrentItem();
		ItemStack boots = Inventory.armorInventory[0];
		ItemStack leggings = Inventory.armorInventory[1];
		ItemStack chestplate = Inventory.armorInventory[2];
		ItemStack helmet = Inventory.armorInventory[3];
		
		if (event.isCanceled() || allNull(current, boots, leggings, chestplate, helmet) || minecraftInstance.thePlayer.capabilities.isCreativeMode || event.type != ElementType.EXPERIENCE) return;
		
		ScaledResolution scaled = new ScaledResolution(minecraftInstance, minecraftInstance.displayWidth, minecraftInstance.displayHeight);
		int armorOffset = 16;
		int width = scaled.getScaledWidth() + offsetPosition;
		int height = scaled.getScaledHeight();
		GL11.glColor4f(1F, 1F, 1F, 1F);
		RenderHelper.enableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		boolean armorAllNull = allNull(boots, leggings, chestplate, helmet);
		
		int[] params = new int[] {width, height, armorOffset, armorAllNull ? 1 : 0};
		int[] params2 = new int[] {width, height, 0, armorAllNull ? 1 : 0};
		
		if (corner.name().contains("RIGHT")) {
			params2 = renderItem(current, params, 1);
			if (!armorAllNull) {
				renderArmor(boots, BOOTS, params2, 2);
				renderArmor(leggings, LEGGINGS, params2, 2);
				renderArmor(chestplate, CHESTPLATE, params2, 2);
				renderArmor(helmet, HELMET, params2, 2);
			}
		} else {
			boolean params2gotten = false;
			if (boots != null) {
				if (!params2gotten) {
					params2 = renderArmor(boots, BOOTS, params, 1);
					params2gotten = true;
				} else {
					renderArmor(boots, BOOTS, params, 1);
				}
			}
			if (leggings != null) {
				if (!params2gotten) {
					params2 = renderArmor(leggings, LEGGINGS, params, 1);
					params2gotten = true;
				} else {
					renderArmor(leggings, LEGGINGS, params, 1);
				}
			}
			if (chestplate != null) {
				if (!params2gotten) {
					params2 = renderArmor(chestplate, CHESTPLATE, params, 1);
					params2gotten = true;
				} else {
					renderArmor(chestplate, CHESTPLATE, params, 1);
				}
			}
			if (helmet != null) {
				if (!params2gotten) {
					params2 = renderArmor(helmet, HELMET, params, 1);
					params2gotten = true;
				} else {
					renderArmor(helmet, HELMET, params, 1);
				}
			}
			renderItem(current, params2, 2);
		}
		
		RenderHelper.disableStandardItemLighting();
	}
	
	private void renderItemAndEffectIntoGUI(ItemStack stack, int x, int y) {
		itemRender.renderItemAndEffectIntoGUI(fontRenderer, minecraftInstance.getTextureManager(), stack, x, y);
	}
	
	private int[] renderItem(ItemStack stack, int[] params, int turn) {
		int width = params[0];
		int height = params[1];
		boolean armorAllNull = params[3] == 1 ? true : false;
		int[] retStatement = new int[4];
		retStatement[0] = params[0];
		retStatement[1] = params[1];
		retStatement[2] = params[2];
		retStatement[3] = params[3];
		if (stack != null) {
			if (stack.isItemStackDamageable()) {
				int x = corner.name().contains("LEFT") ? params[2] - (armorAllNull ? offsetPosition : 0) : width - 20;
				int y = corner.name().contains("TOP") ? !armorAllNull ? 16 : 0 : (armorAllNull ? height - 16 : height - 48);
				int y2 = corner.name().contains("BOTTOM") ? y - 16 : y + 16;
				String damage = String.valueOf(stack.getMaxDurability() - stack.getCurrentDurability());
				if (stack.hasTagCompound()) {
					if (stack.getTagCompound().hasKey("InfiTool")) damage = "";
				}
				int damageStringWidth = fontRenderer.getStringWidth(damage) + 2;
				renderItemAndEffectIntoGUI(stack, x - (corner.name().contains("LEFT") ? 0 : damageStringWidth - 2), y);
				if (stack.getItem() instanceof ItemBow) {
					int arrows = getArrowsInInventory();
					if (arrows > 0) {
						renderItemAndEffectIntoGUI(new ItemStack(Items.arrow), x - (corner.name().contains("LEFT") ? 0 : damageStringWidth - 2), y2);
						fontRenderer.drawString(String.valueOf(arrows), corner.name().contains("RIGHT") ? (x - damageStringWidth + 18) : x + 18, y2 + (fontRenderer.FONT_HEIGHT / 2), color_white);
					}
					fontRenderer.drawString(String.valueOf(damage), corner.name().contains("RIGHT") ? (x - damageStringWidth + 18) : x + 18, y + (fontRenderer.FONT_HEIGHT / 2), color_white);
				} else {
					fontRenderer.drawString(String.valueOf(damage), corner.name().contains("RIGHT") ? (x - damageStringWidth + 18) : x + 18, y + (fontRenderer.FONT_HEIGHT / 2), color_white);
				}
				retStatement[2] = damageStringWidth + 34;
				if (turn == 1 && armorAllNull) setCloseSize(18 + damageStringWidth);
				if (turn == 2 && armorAllNull) setCloseSize(18 + damageStringWidth);
				if (turn == 2 && !armorAllNull) setCloseSize(fontRenderer.getStringWidth("9999") + 36 + damageStringWidth);
			}
		}
		return retStatement;
	}
	
	private int[] renderArmor(ItemStack stack, int type, int[] params, int turn) {
		int width = params[0];
		int height = params[1];
		int armorOffset = params[2];
		int[] retStatement = new int[4];
		retStatement[0] = params[0];
		retStatement[1] = params[1];
		retStatement[3] = params[3];
		if (stack != null) {
			int x = (corner.name().contains("LEFT")) ? 0 + (armorOffset - 16) - offsetPosition : width - armorOffset;
			int y = (corner.name().contains("TOP")) ? (4 - type) * 16 : height - (16 * type);
			String damage = String.valueOf(stack.getMaxDurability() - stack.getCurrentDurability());
			int damageStringWidth = corner.name().contains("LEFT") ? Math.max(fontRenderer.getStringWidth(damage) + 2, fontRenderer.getStringWidth("9999") + 2) : fontRenderer.getStringWidth(damage) + 2;
			if (corner.name().contains("LEFT")) x += damageStringWidth;
			renderItemAndEffectIntoGUI(stack, x, y);
			fontRenderer.drawString(String.valueOf(damage), x - (corner.name().contains("LEFT") ? (damageStringWidth - 2) : damageStringWidth), y + (fontRenderer.FONT_HEIGHT / 2), color_white);
			retStatement[2] = x + 18;
			if (turn == 2) setCloseSize(16 + damageStringWidth + armorOffset);
		}
		return retStatement;
	}
}