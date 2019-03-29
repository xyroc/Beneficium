package xiroc.beneficium.item;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xiroc.beneficium.Beneficium;

public class ItemTalismanOfTheBenefactor extends Item {

	public ItemTalismanOfTheBenefactor() {
		setRegistryName("talisman");
		setMaxStackSize(1);
		setHasSubtypes(true);
		setCreativeTab(Beneficium.tabBeneficium);
		addPropertyOverride(new ResourceLocation("type"), new IItemPropertyGetter() {

			@Override
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
				return stack.getItemDamage();
			}
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		items.add(new ItemStack(this, 1, 1));
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
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		switch (stack.getItemDamage()) {
		case 0:
			tooltip.add(TextFormatting.GREEN + "Increases XP earned from XP orbs to "
					+ (int) (Beneficium.XP_MULTIPLIER * 100) + "%");
			break;
		case 1:
			tooltip.add(TextFormatting.GRAY + "" + TextFormatting.ITALIC
					+ "A lens made of an unknown material to concentrate the light of wisdom itself. This holy artifact has been bound to a charm.");
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Increases XP earned from XP orbs to "
					+ (int) (Beneficium.XP_MULTIPLIER_DIVINE * 100) + "%");
			break;
		}
	}

}
