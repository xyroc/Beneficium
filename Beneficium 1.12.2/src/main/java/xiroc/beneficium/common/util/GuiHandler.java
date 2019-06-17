package xiroc.beneficium.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import xiroc.beneficium.client.gui.GuiTreasureChest;
import xiroc.beneficium.common.block.tileentity.TileEntityTreasureChest;
import xiroc.beneficium.common.container.ContainerTreasureChest;

public class GuiHandler implements IGuiHandler {

	public static final int ID_TREASURE = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case ID_TREASURE:
			return new ContainerTreasureChest(player.inventory,
					(TileEntityTreasureChest) world.getTileEntity(new BlockPos(x, y, z)), player);
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case ID_TREASURE:
			return new GuiTreasureChest((ContainerTreasureChest) getServerGuiElement(ID, player, world, x, y, z));
		default:
			return null;
		}
	}

}
