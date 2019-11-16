package xiroc.beneficium.common.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import xiroc.beneficium.Beneficium;
import xiroc.beneficium.common.block.BlockRegistry;
import xiroc.beneficium.common.block.tileentity.TileEntityTreasureChest;
import xiroc.beneficium.common.loot.LootTableHelper;
import xiroc.beneficium.common.loot.TreasurePositionFile;

public class WorldGenTreasureChest implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == 0 && random.nextFloat() < 0.03F) {
			for (int i = 0; i < 16; i++) {
				if (!world.isChunkGeneratedAt(chunkX, chunkZ))
					continue;
				int x = chunkX * 16 + 1 + random.nextInt(15);
				int z = chunkZ * 16 + 1 + random.nextInt(15);
				int y = getSurfaceHeight(world, x, z) - (4 + random.nextInt(5));
				BlockPos pos = new BlockPos(x, y, z);
				if (!world.isBlockLoaded(pos) || y < 1 || !world.getBlockState(pos).isOpaqueCube())
					continue;
				world.setBlockState(pos, BlockRegistry.treasure.getDefaultState());
				TileEntityTreasureChest treasure = (TileEntityTreasureChest) world.getTileEntity(pos);
				if (treasure == null)
					continue;
				if (!world.isRemote) {
					LootTable lootTable = world.getLootTableManager()
							.getLootTableFromLocation(Beneficium.locate("chests/treasure"));
					if (lootTable == null) {
						Beneficium.logger.error("Loot table beneficium:chests/treasure appears to be null.");
						return;
					}
					LootContext ctx = new LootContext.Builder((WorldServer) world).build();
					lootTable.fillInventory(treasure, random, ctx);
					TreasurePositionFile file = TreasurePositionFile.files.get(world);
					if (file == null) {
						Beneficium.logger.error("Treasure position file for "
								+ world.provider.getDimensionType().getName() + " is null.");
						return;
					}
					file.addPositon(pos);
				}
				return;
			}
			Beneficium.logger.info("All 16 treasure spawn tries in chunk [" + chunkX + "," + chunkZ + "] failed");
		}
	}

	private int getSurfaceHeight(World world, int x, int z) {
		for (int y = world.getActualHeight(); y > 0; y--) {
			if (world.getBlockState(new BlockPos(x, y, z)).isOpaqueCube())
				return y;
		}
		return 0;
	}

}
