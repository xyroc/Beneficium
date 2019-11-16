package xiroc.beneficium.common.proxy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import xiroc.beneficium.Beneficium;
import xiroc.beneficium.client.render.tileentity.TileEntityTreasureChestRenderer;
import xiroc.beneficium.common.block.BlockRegistry;
import xiroc.beneficium.common.block.tileentity.TileEntityTreasureChest;
import xiroc.beneficium.common.item.ItemRegistry;

public class ClientProxy extends ServerProxy {
	
	@Override
	public void load() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTreasureChest.class,
				new TileEntityTreasureChestRenderer());
	}

}
