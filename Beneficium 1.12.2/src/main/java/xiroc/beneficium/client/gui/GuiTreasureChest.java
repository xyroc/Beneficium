package xiroc.beneficium.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import xiroc.beneficium.Beneficium;
import xiroc.beneficium.common.container.ContainerTreasureChest;

public class GuiTreasureChest extends GuiContainer {

	public static final ResourceLocation TEXTURE = Beneficium.locate("textures/gui/treasure.png");

	private ContainerTreasureChest container;

	public GuiTreasureChest(ContainerTreasureChest inventorySlotsIn) {
		super(inventorySlotsIn);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString("Treasure Chest", 8, 6, 4210752);
		this.fontRenderer.drawString("Inventory", 8, this.ySize - 92, 4210752);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		int k = (this.width - 176) / 2;
		int l = (this.height - 168) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, 176, 168);
	}

}
