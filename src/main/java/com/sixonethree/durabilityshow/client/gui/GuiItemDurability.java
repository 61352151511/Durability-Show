package com.sixonethree.durabilityshow.client.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiItemDurability extends Gui {
	private static Minecraft minecraftInstance;
	private static EnumGuiState guiState = EnumGuiState.OPEN;
	private static EnumCorner corner = EnumCorner.BOTTOM_RIGHT;
	private static int offsetPosition = 0;
	private static int closeSize = 16;
	private static int color_white = Color.WHITE.getRGB();
	private static FontRenderer fontRenderer;
	private static RenderItem itemRender;
	private static boolean renderCharacter = false;
	private static boolean renderBaubles = false;
	private static int overrideRenderCharacterTime = 0;
	private static Object[][] lastArmorSet = new Object[][] {
		new String[] {
			"", "", "", ""
		},
		new Integer[] {
			0, 0, 0, 0
		}
	};
	
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
	public static int getOverrideTime() { return overrideRenderCharacterTime; }
	public static void decOverrideTime() { overrideRenderCharacterTime --; }
	public static boolean getRenderCharacter() { return renderCharacter; }
	public static void setRenderChararcter(boolean render) { renderCharacter = render; }
	public static boolean getRenderBaubles() { return renderBaubles; }
	public static void setRenderBaubles(boolean render) { renderBaubles = render; }
	
	public GuiItemDurability(Minecraft MC) {
		super();
		minecraftInstance = MC;
		fontRenderer = MC.fontRendererObj;
		itemRender = MC.getRenderItem();
	}
	
	private int getArrowsInInventory() {
		int arrows = 0;
		for (ItemStack stack : minecraftInstance.player.inventory.mainInventory) {
			if (!stack.isEmpty()) {
				if (stack.getItem() instanceof ItemArrow) {
					arrows += stack.getCount();
				}
			}
		}
		return arrows;
	}
	
	private ItemStack getArrowToDraw() {
		if (this.isArrow(minecraftInstance.player.getHeldItem(EnumHand.OFF_HAND))) {
			return minecraftInstance.player.getHeldItem(EnumHand.OFF_HAND);
		} else if (this.isArrow(minecraftInstance.player.getHeldItem(EnumHand.MAIN_HAND))) {
			return minecraftInstance.player.getHeldItem(EnumHand.MAIN_HAND);
		} else {
			for (int i = 0; i < minecraftInstance.player.inventory.getSizeInventory(); i ++) {
				ItemStack itemstack = minecraftInstance.player.inventory.getStackInSlot(i);
				if (this.isArrow(itemstack)) { return itemstack; }
			}
			return null;
		}
	}
	
	protected boolean isArrow(ItemStack stack) {
		return !stack.isEmpty() && stack.getItem() instanceof ItemArrow;
	}
	
	public boolean allNull(ItemStack... stacks) {
		for (ItemStack s : stacks) {
			if (!s.isEmpty()) return false;
		}
		return true;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL) public void onRender(RenderGameOverlayEvent.Post event) {
		EntityPlayer effectivePlayer = minecraftInstance.player;
		boolean noSpec = false;
		if (minecraftInstance.player.isSpectator()) {
			Entity spec = minecraftInstance.getRenderViewEntity();
			if (spec != null) {
				if (spec instanceof EntityPlayer) {
					effectivePlayer = (EntityPlayer) spec;
				} else {
					noSpec = true;
				}
			} else {
				noSpec = true;
			}
		}
		
		ItemStack current = effectivePlayer.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
		ItemStack secondary = effectivePlayer.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
		ItemStack boots = effectivePlayer.getItemStackFromSlot(EntityEquipmentSlot.FEET);
		ItemStack leggings = effectivePlayer.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		ItemStack chestplate = effectivePlayer.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		ItemStack helmet = effectivePlayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		
		RayTraceResult rayTraceResult = minecraftInstance.objectMouseOver;
		if (rayTraceResult != null && rayTraceResult.typeOfHit != null && rayTraceResult.typeOfHit == Type.ENTITY) {
			if (rayTraceResult.entityHit instanceof EntityArmorStand) {
				EntityArmorStand stand = (EntityArmorStand) rayTraceResult.entityHit;
				current = stand.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
				secondary = stand.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
				boots = stand.getItemStackFromSlot(EntityEquipmentSlot.FEET);
				leggings = stand.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
				chestplate = stand.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
				helmet = stand.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
			}
		}
		
		if (event.isCanceled() ||
			allNull(current, boots, leggings, chestplate, helmet) ||
			minecraftInstance.player.capabilities.isCreativeMode ||
			noSpec ||
			event.getType() != ElementType.EXPERIENCE) return;
		
		/* Compare to last armor set */
		
		String curHelmetName = "";
		String curChestplateName = "";
		String curLeggingsName = "";
		String curBootsName = "";
		Integer curHelmetDur = 0;
		Integer curChestplateDur = 0;
		Integer curLeggingsDur = 0;
		Integer curBootsDur = 0;
		
		if (!helmet.isEmpty()) {
			curHelmetName = helmet.getUnlocalizedName();
			curHelmetDur = helmet.getItemDamage();
		}
		if (!chestplate.isEmpty()) {
			curChestplateName = chestplate.getUnlocalizedName();
			curChestplateDur = chestplate.getItemDamage();
		}
		if (!leggings.isEmpty()) {
			curLeggingsName = leggings.getUnlocalizedName();
			curLeggingsDur = leggings.getItemDamage();
		}
		if (!boots.isEmpty()) {
			curBootsName = boots.getUnlocalizedName();
			curBootsDur = boots.getItemDamage();
		}
		
		String lastHelmetName = (String) lastArmorSet[0][0];
		String lastChestplateName = (String) lastArmorSet[0][1];
		String lastLeggingsName = (String) lastArmorSet[0][2];
		String lastBootsName = (String) lastArmorSet[0][3];
		if (!lastHelmetName.equalsIgnoreCase(curHelmetName) ||
			lastArmorSet[1][0] != Integer.valueOf(curHelmetDur) ||
			!lastChestplateName.equalsIgnoreCase(curChestplateName) ||
			lastArmorSet[1][1] != Integer.valueOf(curChestplateDur) ||
			!lastLeggingsName.equalsIgnoreCase(curLeggingsName) ||
			lastArmorSet[1][2] != Integer.valueOf(curLeggingsDur) ||
			!lastBootsName.equalsIgnoreCase(curBootsName) ||
			lastArmorSet[1][3] != Integer.valueOf(curBootsDur)) {
			overrideRenderCharacterTime = 40;
		}
		
		lastArmorSet[0][0] = curHelmetName;
		lastArmorSet[0][1] = curChestplateName;
		lastArmorSet[0][2] = curLeggingsName;
		lastArmorSet[0][3] = curBootsName;
		lastArmorSet[1][0] = curHelmetDur;
		lastArmorSet[1][1] = curChestplateDur;
		lastArmorSet[1][2] = curLeggingsDur;
		lastArmorSet[1][3] = curBootsDur;
		
		/* Begin rendering */
		
		ScaledResolution scaled = new ScaledResolution(minecraftInstance);
		
		if (renderCharacter && overrideRenderCharacterTime <= 0) {
			renderCharacter(corner, 10, scaled, effectivePlayer);
		} else {
			if (renderBaubles && Loader.isModLoaded("baubles")) {
				baubles.api.cap.IBaublesItemHandler handler = baubles.api.BaublesApi.getBaublesHandler(effectivePlayer);
				ItemStack amulet = handler.getStackInSlot(0);
				ItemStack ring1 = handler.getStackInSlot(1);
				ItemStack ring2 = handler.getStackInSlot(2);
				ItemStack belt = handler.getStackInSlot(3);
				ItemStack head = handler.getStackInSlot(4);
				ItemStack body = handler.getStackInSlot(5);
				ItemStack charm = handler.getStackInSlot(6);
				
				int width = scaled.getScaledWidth() + offsetPosition;
				int height = scaled.getScaledHeight();
				GlStateManager.color(1, 1, 1, 1);
				RenderHelper.enableStandardItemLighting();
				RenderHelper.enableGUIStandardItemLighting();
				
				int baubleNumber = 0;
				baubleNumber += renderBauble(amulet, baubleNumber, width, height);
				baubleNumber += renderBauble(ring1, baubleNumber, width, height);
				baubleNumber += renderBauble(ring2, baubleNumber, width, height);
				baubleNumber += renderBauble(belt, baubleNumber, width, height);
				baubleNumber += renderBauble(head, baubleNumber, width, height);
				baubleNumber += renderBauble(body, baubleNumber, width, height);
				baubleNumber += renderBauble(charm, baubleNumber, width, height);
				
				RenderHelper.disableStandardItemLighting();
			} else {
				int armorOffset = 16;
				int width = scaled.getScaledWidth() + offsetPosition;
				int height = scaled.getScaledHeight();
				GlStateManager.color(1, 1, 1, 1);
				RenderHelper.enableStandardItemLighting();
				RenderHelper.enableGUIStandardItemLighting();
				boolean armorAllNull = allNull(boots, leggings, chestplate, helmet);
				
				int[] params = new int[] {width, height, armorOffset, armorAllNull ? 1 : 0};
				int[] params2 = new int[] {width, height, 0, armorAllNull ? 1 : 0};
				
				if (corner.name().contains("RIGHT")) {
					params2 = renderItem(current, secondary, params, 1);
					if (!armorAllNull) {
						renderArmor(boots, BOOTS, params2, 2);
						renderArmor(leggings, LEGGINGS, params2, 2);
						renderArmor(chestplate, CHESTPLATE, params2, 2);
						renderArmor(helmet, HELMET, params2, 2);
					}
				} else {
					boolean params2gotten = false;
					if (!boots.isEmpty()) {
						if (!params2gotten) {
							params2 = renderArmor(boots, BOOTS, params, 1);
							params2gotten = true;
						} else {
							renderArmor(boots, BOOTS, params, 1);
						}
					}
					if (!leggings.isEmpty()) {
						if (!params2gotten) {
							params2 = renderArmor(leggings, LEGGINGS, params, 1);
							params2gotten = true;
						} else {
							renderArmor(leggings, LEGGINGS, params, 1);
						}
					}
					if (!chestplate.isEmpty()) {
						if (!params2gotten) {
							params2 = renderArmor(chestplate, CHESTPLATE, params, 1);
							params2gotten = true;
						} else {
							renderArmor(chestplate, CHESTPLATE, params, 1);
						}
					}
					if (!helmet.isEmpty()) {
						if (!params2gotten) {
							params2 = renderArmor(helmet, HELMET, params, 1);
							params2gotten = true;
						} else {
							renderArmor(helmet, HELMET, params, 1);
						}
					}
					renderItem(current, secondary, params2, 2);
				}
				RenderHelper.disableStandardItemLighting();
			}
		}
	}
	
	private void renderItemAndEffectIntoGUI(ItemStack stack, int x, int y) {
		itemRender.renderItemAndEffectIntoGUI(stack, x, y);
	}
	
	private int[] renderItem(ItemStack mainHand, ItemStack offHand, int[] params, int turn) {
		int width = params[0];
		int height = params[1];
		boolean armorAllNull = params[3] == 1 ? true : false;
		int[] retStatement = new int[4];
		retStatement[0] = params[0];
		retStatement[1] = params[1];
		retStatement[2] = params[2];
		retStatement[3] = params[3];

		ItemStack firstStack = ItemStack.EMPTY;
		ItemStack secondStack = ItemStack.EMPTY;
		if (mainHand.isEmpty() && offHand.isEmpty()) return retStatement;
		if (mainHand.isEmpty()) {
			if (offHand.isItemStackDamageable()) firstStack = offHand;
		} else {
			if (mainHand.isItemStackDamageable()) {
				firstStack = mainHand;
				if (!offHand.isEmpty()) {
					if (offHand.isItemStackDamageable()) secondStack = offHand;
				}
			} else {
				if (!offHand.isEmpty()) {
					if (offHand.isItemStackDamageable()) firstStack = offHand;
				}
			}
		}
		
		if (firstStack.isEmpty() && secondStack.isEmpty()) return retStatement;
		
		boolean mainBow = firstStack.getItem() instanceof ItemBow;
		boolean secondaryBow = !secondStack.isEmpty() ? secondStack.getItem() instanceof ItemBow : false;
		
		int itemX = corner.name().contains("LEFT") ? params[2] - (armorAllNull ? offsetPosition : 0) : width - 20;
		int mainHandY = corner.name().contains("TOP") ? !armorAllNull ? 16 : 0 : (armorAllNull ? height - 16 : height - 48);
		int arrowY = mainHandY + 16;
		int offHandY = mainHandY + ((mainBow || secondaryBow) && (getArrowsInInventory() > 0) ? 32 : 16);
		
		String mainDamage = String.valueOf(firstStack.getMaxDamage() - firstStack.getItemDamage());
		int damageStringWidth = fontRenderer.getStringWidth(mainDamage) + 2;
		renderItemAndEffectIntoGUI(firstStack, itemX - (corner.name().contains("LEFT") ? 0 : damageStringWidth - 2), mainHandY);
		if (firstStack.getItem() instanceof ItemBow) {
			int arrows = getArrowsInInventory();
			if (arrows > 0) {
				renderItemAndEffectIntoGUI(getArrowToDraw(), itemX - (corner.name().contains("LEFT") ? 0 : damageStringWidth - 2), arrowY);
				fontRenderer.drawString(String.valueOf(arrows), corner.name().contains("RIGHT") ? (itemX - damageStringWidth + 18) : itemX + 18, arrowY + (fontRenderer.FONT_HEIGHT / 2), color_white);
			}
		}
		fontRenderer.drawString(String.valueOf(mainDamage), corner.name().contains("RIGHT") ? (itemX - damageStringWidth + 18) : itemX + 18, mainHandY + (fontRenderer.FONT_HEIGHT / 2), color_white);
		if (!secondStack.isEmpty()) {
			String offHandDamage = String.valueOf(secondStack.getMaxDamage() - secondStack.getItemDamage());
			damageStringWidth = Math.max(damageStringWidth, fontRenderer.getStringWidth(offHandDamage) + 2);
			renderItemAndEffectIntoGUI(secondStack, itemX - (corner.name().contains("LEFT") ? 0 : damageStringWidth - 2), offHandY);
			if (secondStack.getItem() instanceof ItemBow && (!(firstStack.getItem() instanceof ItemBow))) {
				int arrows = getArrowsInInventory();
				if (arrows > 0) {
					renderItemAndEffectIntoGUI(getArrowToDraw(), itemX - (corner.name().contains("LEFT") ? 0 : damageStringWidth - 2), arrowY);
					fontRenderer.drawString(String.valueOf(arrows), corner.name().contains("RIGHT") ? (itemX - damageStringWidth + 18) : itemX + 18, arrowY + (fontRenderer.FONT_HEIGHT / 2), color_white);
				}
			}
			fontRenderer.drawString(String.valueOf(offHandDamage), corner.name().contains("RIGHT") ? (itemX - damageStringWidth + 18) : itemX + 18, offHandY + (fontRenderer.FONT_HEIGHT / 2), color_white);
		}
		
		retStatement[2] = damageStringWidth + 34;
		if (turn == 1 && armorAllNull) setCloseSize(18 + damageStringWidth);
		if (turn == 2 && armorAllNull) setCloseSize(18 + damageStringWidth);
		if (turn == 2 && !armorAllNull) setCloseSize(fontRenderer.getStringWidth("9999") + 36 + damageStringWidth);
		
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
		if (!stack.isEmpty()) {
			int x = (corner.name().contains("LEFT")) ? 0 + (armorOffset - 16) - offsetPosition : width - armorOffset;
			int y = (corner.name().contains("TOP")) ? (4 - type) * 16 : height - (16 * type);
			String damage = String.valueOf(stack.getMaxDamage() - stack.getItemDamage());
			int damageStringWidth = corner.name().contains("LEFT") ? Math.max(fontRenderer.getStringWidth(damage) + 2, fontRenderer.getStringWidth("9999") + 2) : fontRenderer.getStringWidth(damage) + 2;
			if (corner.name().contains("LEFT")) x += damageStringWidth;
			renderItemAndEffectIntoGUI(stack, x, y);
			fontRenderer.drawString(String.valueOf(damage), x - (corner.name().contains("LEFT") ? (damageStringWidth - 2) : damageStringWidth), y + (fontRenderer.FONT_HEIGHT / 2), color_white);
			retStatement[2] = x + 18;
			if (turn == 2) setCloseSize(16 + damageStringWidth + armorOffset);
		}
		return retStatement;
	}
	
	private int renderBauble(ItemStack stack, int baubleNumber, int width, int height) {
		if (stack.isEmpty()) return 0;
		if (!stack.isItemStackDamageable()) return 0;
		
		int x = (corner.name().contains("LEFT")) ? 0 - offsetPosition : width - 16;
		int y = (corner.name().contains("TOP")) ? baubleNumber * 16 : height - (baubleNumber + 1) * 16;
		String damage = String.valueOf(stack.getMaxDamage() - stack.getItemDamage());
		renderItemAndEffectIntoGUI(stack, x, y);
		int textX = x + (corner.name().contains("LEFT") ? 16 : -(fontRenderer.getStringWidth(damage)));
		fontRenderer.drawString(damage, textX, y + (fontRenderer.FONT_HEIGHT / 2), color_white);
		setCloseSize(fontRenderer.getStringWidth("9999") + 16);
		return 1;
	}
	
	private void renderCharacter(EnumCorner side, int xPos, ScaledResolution scaled, EntityPlayer effectivePlayer) {
		if (side.name().contains("LEFT")) {
			GuiInventory.drawEntityOnScreen(xPos - offsetPosition, scaled.getScaledHeight(), xPos - (((xPos / 2) * -1) * 2), -50, - effectivePlayer.rotationPitch, effectivePlayer);
		} else {
			GuiInventory.drawEntityOnScreen((scaled.getScaledWidth() - xPos) + offsetPosition, scaled.getScaledHeight(), xPos - (((xPos / 2) * -1) * 2), 50, - effectivePlayer.rotationPitch, effectivePlayer);
		}
	}
}