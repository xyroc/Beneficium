package xiroc.beneficium.item;

import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xiroc.beneficium.Beneficium;

public class ItemTalismanOfTheBenefactor extends Item {

	public ItemTalismanOfTheBenefactor() {
		setRegistryName("TalismanOfTheBenefactor");
		setMaxStackSize(1);
		setHasSubtypes(true);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		super.getSubItems(itemIn, tab, subItems);
		subItems.add(new ItemStack(itemIn, 1, 1));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case 0:
			return "item.talisman";
		case 1:
			return "item.divine_talisman";
		default:
			return "item.talisman";
		}
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public int getMetadata(ItemStack stack) {
		return stack.getItemDamage();
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case 0:
			return EnumRarity.RARE;
		case 1:
			return EnumRarity.EPIC;
		default:
			return EnumRarity.RARE;
		}
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		switch (stack.getItemDamage()) {
		case 0:
			tooltip.add(EnumChatFormatting.GREEN + "Increases XP earned from XP orbs to " + (int) (Beneficium.XP_MULTIPLIER * 100)
					+ "%");
			break;
		case 1: {
			tooltip.add(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC
					+ "A lens made of an unknown material to concentrate the light of wisdom itself. This holy artifact has been bound to a charm.");
			tooltip.add(EnumChatFormatting.LIGHT_PURPLE + "Increases XP earned from XP orbs to "
					+ (int) (Beneficium.XP_MULTIPLIER_DIVINE * 100) + "%");
		}
		}
	}

}
