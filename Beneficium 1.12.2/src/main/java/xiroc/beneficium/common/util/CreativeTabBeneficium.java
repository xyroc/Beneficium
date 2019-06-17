package xiroc.beneficium.common.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xiroc.beneficium.common.item.ItemRegistry;

public class CreativeTabBeneficium extends CreativeTabs{

	public CreativeTabBeneficium() {
		super("tabBeneficium");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ItemRegistry.talismanOfTheBenefactor);
	}

}
