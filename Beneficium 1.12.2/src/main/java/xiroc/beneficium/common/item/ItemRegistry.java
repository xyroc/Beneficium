package xiroc.beneficium.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import xiroc.beneficium.common.block.BlockRegistry;

public class ItemRegistry {
	
	public static final Item talismanOfTheBenefactor = new ItemTalismanOfTheBenefactor();
	public static final ItemPickaxe artifactPickaxe = new ItemArtifactPickaxe();
	public static final Item guardiansCharm = new ItemGuardiansCharm();
	
	public static final Item treasure = new ItemBlockTreasureChest(BlockRegistry.treasure).setRegistryName(BlockRegistry.treasure.getRegistryName());

}
