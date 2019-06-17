package xiroc.beneficium.common.item;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import xiroc.beneficium.Beneficium;

public class ItemGuardiansCharm extends Item implements IBauble {

	public ItemGuardiansCharm() {
		setRegistryName("guardians_charm");
		setUnlocalizedName("guardians_charm");
		setMaxStackSize(1);
		setCreativeTab(Beneficium.tabBeneficium);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (player.getHealth() < 5 && player.ticksExisted % 39 == 0) {
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 40, 2));
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40, 2));
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, .75F, 1.9f);
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, .75F, 2f);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.CHARM;
	}

}
