package xiroc.beneficium.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xiroc.beneficium.Beneficium;

public class EventManager {

	@SubscribeEvent
	public void onEXP(PlayerPickupXpEvent event) {
		if(event.entityPlayer.worldObj.isRemote) return;
		switch (getTalismanLevel(event.entityPlayer.inventory.mainInventory)) {
		case 0:
			return;
		case 1: {
			long xp = (long) (Beneficium.XP_MULTIPLIER * event.orb.xpValue);
			if (xp > Integer.MAX_VALUE) {
				Beneficium.logger.warn("The multiplied exp exceeds the maximum value! Returning Integer.MAX_VALUE");
				xp = Integer.MAX_VALUE;
			}
			event.orb.xpValue = (int) xp;
			break;
		}
		case 2: {
			long xp = (long) (Beneficium.XP_MULTIPLIER_DIVINE * event.orb.xpValue);
			if (xp > Integer.MAX_VALUE) {
				Beneficium.logger.warn("The multiplied exp exceeds the maximum value! Returning Integer.MAX_VALUE");
				xp = Integer.MAX_VALUE;
			}
			event.orb.xpValue = (int) xp;
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

}
