package xiroc.beneficium.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.MinecraftForge;
import xiroc.beneficium.Beneficium;

public class ItemTalismanOfTheBenefactor extends Item {

	private IIcon[] textures;

	public ItemTalismanOfTheBenefactor() {
		setMaxStackSize(1);
		setHasSubtypes(true);
		setCreativeTab(Beneficium.tabBeneficium);
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		textures = new IIcon[2];
		textures[0] = iconRegister.registerIcon("beneficium:talisman");
		textures[1] = iconRegister.registerIcon("beneficium:divine_talisman");
	}

	@Override
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		super.getSubItems(p_150895_1_, p_150895_2_, p_150895_3_);
		p_150895_3_.add(new ItemStack(this, 1, 1));
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		switch (stack.getItemDamage()) {
		case 0:
			return textures[0];
		case 1:
			return textures[1];
		default:
			return textures[0];
		}
	}

	@Override
	public IIcon getIconFromDamage(int itemDamage) {
		if (itemDamage > textures.length - 1)
			return textures[0];
		return textures[itemDamage];
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
	public EnumRarity getRarity(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case 0:
			return EnumRarity.rare;
		case 1:
			return EnumRarity.epic;
		default:
			return EnumRarity.rare;
		}
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		switch (par1ItemStack.getItemDamage()) {
		case 0:
			par3List.add(EnumChatFormatting.GREEN + "Increases XP earned from XP orbs to "
					+ (int) (Beneficium.XP_MULTIPLIER * 100) + "%");
			break;
		case 1:
			par3List.add(
					EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "A lens made of an unknown material");
			par3List.add(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC
					+ "to concentrate the light of wisdom itself.");
			par3List.add(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC
					+ "This holy artifact has been bound to a charm.");
			par3List.add(EnumChatFormatting.LIGHT_PURPLE + "Increases XP earned from XP orbs to "
					+ (int) (Beneficium.XP_MULTIPLIER_DIVINE * 100) + "%");
			break;
		}
	}

}
