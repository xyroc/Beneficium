package xiroc.beneficium.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import xiroc.beneficium.client.render.tileentity.item.TileEntityItemStackRendererTreasureChest;

public class ItemBlockTreasureChest extends ItemBlock {

	public ItemBlockTreasureChest(Block block) {
		super(block);
		setTileEntityItemStackRenderer(new TileEntityItemStackRendererTreasureChest());
	}
	
}
