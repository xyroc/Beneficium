package xiroc.beneficium.common.loot;

import net.minecraft.world.storage.loot.LootTableList;
import xiroc.beneficium.Beneficium;

public class LootTableHelper {

	public static void registerLootTables() {
		LootTableList.register(Beneficium.locate("chests/treasure"));
	}
}
