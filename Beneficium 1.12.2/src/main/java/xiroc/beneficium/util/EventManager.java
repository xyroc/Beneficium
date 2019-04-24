package xiroc.beneficium.util;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.LootTableLoadEvent;
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
	public void loadloot(LootTableLoadEvent event) {
		// minecraft:chests/stronghold_corridor, minecraft:chests/stronghold_crossing,
		// chests/simple_dungeon, minecraft:chests/nether_bridge,
		// minecraft:chests/abandoned_mineshaft, minecraft:chests/jungle_temple,
		// minecraft:chests/desert_pyramid, minecraft:chests/village_blacksmith
		switch (event.getName().toString()) {
		case "minecraft:chests/stronghold_corridor": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootEntryDamaged talisman = new LootEntryDamaged(Beneficium.talismanOfTheBenefactor,
					Beneficium.WEIGHT_DIVINE_TALISMAN, 1, new LootFunction[0], new LootCondition[0], 1,
					"beneficium:divine_talisman");
			LootPool pool = new LootPool(new LootEntry[] { talisman }, new LootCondition[0], new RandomValueRange(1),
					new RandomValueRange(0, 0), "beneficium:divine_talisman_pool");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/stronghold_crossing": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootEntryItem obsidian = new LootEntryItem(Item.getItemFromBlock(Blocks.OBSIDIAN),
					Beneficium.WEIGHT_DIVINE_TALISMAN, 1, new LootFunction[0], new LootCondition[0],
					"beneficium:divine_talisman");
			LootPool pool = new LootPool(new LootEntry[] { obsidian }, new LootCondition[0], new RandomValueRange(1),
					new RandomValueRange(0, 0), "beneficium:divine_talisman_pool");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/simple_dungeon": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootEntryDamaged talisman = new LootEntryDamaged(Beneficium.talismanOfTheBenefactor,
					Beneficium.WEIGHT_DIVINE_TALISMAN, 1, new LootFunction[0], new LootCondition[0], 1,
					"beneficium:divine_talisman");
			LootPool pool = new LootPool(new LootEntry[] { talisman }, new LootCondition[0], new RandomValueRange(1),
					new RandomValueRange(0, 0), "beneficium:divine_talisman_pool");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/nether_bridge": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootEntryDamaged talisman = new LootEntryDamaged(Beneficium.talismanOfTheBenefactor,
					Beneficium.WEIGHT_DIVINE_TALISMAN, 1, new LootFunction[0], new LootCondition[0], 1,
					"beneficium:divine_talisman");
			LootPool pool = new LootPool(new LootEntry[] { talisman }, new LootCondition[0], new RandomValueRange(1),
					new RandomValueRange(0, 0), "beneficium:divine_talisman_pool");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/jungle_temple": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootEntryDamaged talisman = new LootEntryDamaged(Beneficium.talismanOfTheBenefactor,
					Beneficium.WEIGHT_DIVINE_TALISMAN, 1, new LootFunction[0], new LootCondition[0], 1,
					"beneficium:divine_talisman");
			LootPool pool = new LootPool(new LootEntry[] { talisman }, new LootCondition[0], new RandomValueRange(1),
					new RandomValueRange(0, 0), "beneficium:divine_talisman_pool");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/stronghold_library": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootEntryDamaged talisman = new LootEntryDamaged(Beneficium.talismanOfTheBenefactor,
					Beneficium.WEIGHT_DIVINE_TALISMAN, 1, new LootFunction[0], new LootCondition[0], 1,
					"beneficium:divine_talisman");
			LootPool pool = new LootPool(new LootEntry[] { talisman }, new LootCondition[0], new RandomValueRange(1),
					new RandomValueRange(0, 0), "beneficium:divine_talisman_pool");
			event.getTable().addPool(pool);
			break;
		}
		}
	}

	@SubscribeEvent
	public void onEXP(PlayerPickupXpEvent event) {
		if (event.getEntityPlayer().world.isRemote)
			return;
		switch (getBaubleTalismanLevel(event.getEntityPlayer())) {
		case 0:
			return;
		case 1: {
			if (Beneficium.XP_BOOST_ENABLE_LEVEL_THRESHOLD
					&& event.getEntityPlayer().experienceLevel < Beneficium.XP_BOOST_ENABLE_LEVEL_THRESHOLD_VALUE)
				return;
			long xp = (long) (Beneficium.XP_MULTIPLIER * event.getOrb().xpValue);
			if (xp > Integer.MAX_VALUE) {
				Beneficium.logger.warn("The multiplied exp exceeds the maximum value! Returning Integer.MAX_VALUE");
				xp = Integer.MAX_VALUE;
			}
			event.getOrb().xpValue = (int) xp;
			break;
		}
		case 2: {
			if (Beneficium.XP_BOOST_ENABLE_LEVEL_THRESHOLD
					&& event.getEntityPlayer().experienceLevel < Beneficium.XP_BOOST_ENABLE_LEVEL_THRESHOLD_VALUE)
				return;
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

	public static int getBaubleTalismanLevel(EntityPlayer player) {
		int level = 0;
		IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
		for (int a = 0; a < handler.getSlots(); a++) {
			if (!handler.getStackInSlot(a).isEmpty()
					&& handler.getStackInSlot(a).getItem() == Beneficium.talismanOfTheBenefactor) {
				int meta = handler.getStackInSlot(a).getItemDamage() + 1;
				if (meta > level)
					level = meta;
			}
		}
		return level;
	}

}
