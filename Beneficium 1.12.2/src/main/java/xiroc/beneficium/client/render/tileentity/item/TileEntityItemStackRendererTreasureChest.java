package xiroc.beneficium.client.render.tileentity.item;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import xiroc.beneficium.common.block.tileentity.TileEntityTreasureChest;

public class TileEntityItemStackRendererTreasureChest extends TileEntityItemStackRenderer {

	private final TileEntityTreasureChest chest = new TileEntityTreasureChest();

	@Override
	public void renderByItem(ItemStack p_192838_1_, float partialTicks) {
		TileEntityRendererDispatcher.instance.render(this.chest, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks);
	}

}
