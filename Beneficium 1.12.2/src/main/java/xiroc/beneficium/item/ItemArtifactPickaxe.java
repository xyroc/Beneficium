package xiroc.beneficium.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDigging;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import xiroc.beneficium.Beneficium;
import xiroc.beneficium.network.PacketSound;
import xiroc.beneficium.util.ConfigHelper;

public class ItemArtifactPickaxe extends ItemPickaxe {

	public ItemArtifactPickaxe() {
		super(EnumHelper.addToolMaterial("artifact", 10, 7500, 15F, 3.5F, 10));
		setRegistryName("artifact_pickaxe");
		setUnlocalizedName("artifact_pickaxe");
		setCreativeTab(Beneficium.tabBeneficium);
		setNoRepair();
		addPropertyOverride(new ResourceLocation("mode"), new IItemPropertyGetter() {

			@Override
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
				if (entityIn != null) {
					if (entityIn.isPotionActive(Potion.getPotionFromResourceLocation("haste")))
						return 1;
				}
				return 0;
			}
		});
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!worldIn.isRemote) {
			int cooldown = getCooldown(playerIn.getEntityData());
			if (cooldown <= 0) {
				playerIn.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("haste"),
						ConfigHelper.getInt("artifact_pickaxe_active_duration"), 1));
				playerIn.getEntityData().setInteger("artifact_pickaxe_cooldown",
						ConfigHelper.getInt("artifact_pickaxe_active_cooldown"));
				new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
				Beneficium.NET.sendTo(new PacketSound(0), (EntityPlayerMP) playerIn);
			} else
				playerIn.sendStatusMessage(new TextComponentString("Cooldown: " + cooldown / 20 + "s"), true);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(ChatFormatting.GRAY + "" + ChatFormatting.ITALIC
				+ "An ancient golden pickaxe once used to harvest the countless ore veins found in a now forgotten jungle kingdom.");
		tooltip.add(ChatFormatting.LIGHT_PURPLE + "Active: Grants Haste II for "
				+ ConfigHelper.getInt("artifact_pickaxe_active_duration") / 20 + " seconds. Has a "
				+ (((float) ConfigHelper.getInt("artifact_pickaxe_active_cooldown") / 1200D) + "").substring(0, 3)
				+ " minute cooldown.");
		tooltip.add(ChatFormatting.GREEN + "Right click to activate");
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.EPIC;
	}

	public static int getCooldown(NBTTagCompound nbt) {
		if (!nbt.hasKey("artifact_pickaxe_cooldown"))
			return 0;
		return nbt.getInteger("artifact_pickaxe_cooldown");
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

}
