package xiroc.beneficium.common.loot;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xiroc.beneficium.Beneficium;

public class TreasurePositionFile {

	public static HashMap<World, TreasurePositionFile> files = new HashMap<World, TreasurePositionFile>();

	public File file;
	public ArrayList<BlockPos> newPositions;

	public TreasurePositionFile(World world, File file) {
		this.file = file;
		this.newPositions = new ArrayList<BlockPos>();
		files.put(world, this);
	}

	public void addPositon(int x, int y, int z) {
		this.newPositions.add(new BlockPos(x, y, z));
	}

	public void addPositon(BlockPos position) {
		this.newPositions.add(position);
		if (this.newPositions.size() >= 128)
			write();
	}

	public void write() {
		try {
			boolean newFile = !file.exists();
			if (newFile) {
				if (!file.getParentFile().exists())
					file.getParentFile().mkdirs();
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file, true);
			if (newFile) {
				writer.write("# Treasure Position File | Generated at " + System.currentTimeMillis()
						+ System.lineSeparator());
				writer.flush();
			}
			for (BlockPos pos : newPositions) {
				writer.write(
						"x: " + pos.getX() + " \t\t\ty: " + pos.getY() + " \t\t\tz: " + pos.getZ() + System.lineSeparator());
				writer.flush();
			}
			writer.close();
			newPositions.clear();
		} catch (Exception e) {
			Beneficium.logger.error("Failed to save the treasure position file: " + e.getMessage());
		}
	}

}
