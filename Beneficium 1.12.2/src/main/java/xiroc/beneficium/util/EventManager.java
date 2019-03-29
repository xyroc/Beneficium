package xiroc.beneficium.util;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xiroc.beneficium.Beneficium;

public class EventManager {
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(Beneficium.talismanOfTheBenefactor);
	}

	@SubscribeEvent
	public void registerRenderers(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Beneficium.talismanOfTheBenefactor, 0,
				new ModelResourceLocation(new ResourceLocation("beneficium:talisman"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Beneficium.talismanOfTheBenefactor, 1,
				new ModelResourceLocation(new ResourceLocation("beneficium:talisman"), "inventory"));
	}

	@SubscribeEvent
	public void onEXP(PlayerPickupXpEvent event) {
		if(event.getEntityPlayer().world.isRemote) return;
		switch (getTalismanLevel(event.getEntityPlayer().inventory.mainInventory)) {
		case 0:
			return;
		case 1: {
			long xp = (long) (Beneficium.XP_MULTIPLIER * event.getOrb().xpValue);
			if (xp > Integer.MAX_VALUE) {
				Beneficium.logger.warn("The multiplied exp exceeds the maximum value! Returning Integer.MAX_VALUE");
				xp = Integer.MAX_VALUE;
			}
			event.getOrb().xpValue = (int) xp;
			break;
		}
		case 2: {
			long xp = (long) (Beneficium.XP_MULTIPLIER_DIVINE * event.getOrb().xpValue);
			if (xp > Integer.MAX_VALUE) {
				Beneficium.logger.warn("The multiplied exp exceeds the maximum value! Returning Integer.MAX_VALUE");
				xp = Integer.MAX_VALUE;
			}
			event.getOrb().xpValue = (int) xp;
			break;
		}
		}

	}

	public static int getTalismanLevel(ItemStack[] items) {
		int level = 0;
		for (ItemStack stack : items) {
			if (stack == null)
				continue;
			if (stack.getItem() == Beneficium.talismanOfTheBenefactor) {
				int itemlevel = stack.getMetadata() + 1;
				if (itemlevel > level)
					level = itemlevel;
			}
		}
		return level;
	}
	
	public static int getTalismanLevel(NonNullList<ItemStack> items) {
		int level = 0;
		for (ItemStack stack : items) {
			if (stack == null)
				continue;
			if (stack.getItem() == Beneficium.talismanOfTheBenefactor) {
				int itemlevel = stack.getMetadata() + 1;
				if (itemlevel > level)
					level = itemlevel;
			}
		}
		return level;
	}

}
