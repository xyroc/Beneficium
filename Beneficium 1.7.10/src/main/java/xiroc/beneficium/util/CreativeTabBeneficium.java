package xiroc.beneficium.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import xiroc.beneficium.Beneficium;

public class CreativeTabBeneficium extends CreativeTabs{

	public CreativeTabBeneficium() {
		super("tabBeneficium");
	}

	@Override
	public Item getTabIconItem() {
		return Beneficium.talismanOfTheBenefactor;
	}

}
