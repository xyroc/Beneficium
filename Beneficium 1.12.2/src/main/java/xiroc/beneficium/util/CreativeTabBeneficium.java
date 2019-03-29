package xiroc.beneficium.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xiroc.beneficium.Beneficium;

public class CreativeTabBeneficium extends CreativeTabs{

	public CreativeTabBeneficium() {
		super("tabBeneficium");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Beneficium.talismanOfTheBenefactor);
	}

}
