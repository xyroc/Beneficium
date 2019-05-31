package xiroc.beneficium.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import baubles.common.network.PacketOpenBaublesInventory;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
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
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;
import xiroc.beneficium.Beneficium;
import xiroc.beneficium.item.ItemArtifactPickaxe;
import xiroc.beneficium.loot.LootEntryDamaged;
import xiroc.beneficium.loot.LootPoolItem;
import xiroc.beneficium.network.PacketServerConfig;
import xiroc.beneficium.network.PacketServerConfig;

public class EventManager {

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		// Nothing here atm ;)
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(Beneficium.talismanOfTheBenefactor);
		event.getRegistry().register(Beneficium.artifactPickaxe);
	}

	@SubscribeEvent
	public void registerRenderers(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Beneficium.talismanOfTheBenefactor, 0,
				new ModelResourceLocation(new ResourceLocation("beneficium:talisman"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Beneficium.talismanOfTheBenefactor, 1,
				new ModelResourceLocation(new ResourceLocation("beneficium:talisman"), "inventory"));

		ModelLoader.setCustomModelResourceLocation(Beneficium.artifactPickaxe, 0,
				new ModelResourceLocation(new ResourceLocation("beneficium:artifact_pickaxe"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Beneficium.artifactPickaxe, 1,
				new ModelResourceLocation(new ResourceLocation("beneficium:artifact_pickaxe"), "inventory"));
	}

	@SubscribeEvent
	public void loadloot(LootTableLoadEvent event) {
		// minecraft:chests/stronghold_corridor, minecraft:chests/stronghold_crossing,
		// minecraft:chests/simple_dungeon, minecraft:chests/nether_bridge,
		// minecraft:chests/abandoned_mineshaft, minecraft:chests/jungle_temple,
		// minecraft:chests/desert_pyramid, minecraft:chests/village_blacksmith
		switch (event.getName().toString()) {
		case "minecraft:chests/stronghold_corridor": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootPoolItem pool = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(Beneficium.talismanOfTheBenefactor, 1, 1,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:divine_talisman") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(Beneficium.talismanOfTheBenefactor, 1, 1), new RandomValueRange(1), ConfigHelper.getFloat("divine_talisman_loot_chance"),
					"beneficium:talisman");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/stronghold_crossing": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootPoolItem pool = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(Beneficium.talismanOfTheBenefactor, 1, 1,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:divine_talisman") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(Beneficium.talismanOfTheBenefactor, 1, 1), new RandomValueRange(1), ConfigHelper.getFloat("divine_talisman_loot_chance"),
					"beneficium:talisman");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/simple_dungeon": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootPoolItem pool = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(Beneficium.talismanOfTheBenefactor, 1, 1,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:divine_talisman") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(Beneficium.talismanOfTheBenefactor, 1, 1), new RandomValueRange(1), ConfigHelper.getFloat("divine_talisman_loot_chance"),
					"beneficium:talisman");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/nether_bridge": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootPoolItem pool = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(Beneficium.talismanOfTheBenefactor, 1, 1,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:divine_talisman") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(Beneficium.talismanOfTheBenefactor, 1, 1), new RandomValueRange(1), ConfigHelper.getFloat("divine_talisman_loot_chance"),
					"beneficium:talisman");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/jungle_temple": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootPoolItem pool = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(Beneficium.talismanOfTheBenefactor, 1, 1,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:divine_talisman") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(Beneficium.talismanOfTheBenefactor, 1, 1), new RandomValueRange(1), ConfigHelper.getFloat("divine_talisman_loot_chance"),
					"beneficium:talisman");
			event.getTable().addPool(pool);
			LootPoolItem poolArtifactPickaxe = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(Beneficium.artifactPickaxe, 1, 0,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:artifact_pickaxe") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(Beneficium.artifactPickaxe), new RandomValueRange(1), ConfigHelper.getFloat("artifact_pickaxe_loot_chance"),
					"beneficium:artifact_pickaxe");
			event.getTable().addPool(poolArtifactPickaxe);
			break;
		}
		case "minecraft:chests/stronghold_library": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootPoolItem pool = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(Beneficium.talismanOfTheBenefactor, 1, 1,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:divine_talisman") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(Beneficium.talismanOfTheBenefactor, 1, 1), new RandomValueRange(1), ConfigHelper.getFloat("divine_talisman_loot_chance"),
					"beneficium:talisman");
			event.getTable().addPool(pool);
			break;
		}
		}
	}

	@SubscribeEvent
	public void onJoin(PlayerLoggedInEvent event) {
		Beneficium.logger.info("Sending Config Packet");
		Beneficium.NET.sendTo(new PacketServerConfig(ConfigHelper.cache), (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public void onTick(PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START && !event.player.world.isRemote) {
			NBTTagCompound nbt = event.player.getEntityData();
			if (ItemArtifactPickaxe.getCooldown(nbt) > 0)
				nbt.setInteger("artifact_pickaxe_cooldown", nbt.getInteger("artifact_pickaxe_cooldown") - 1);
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
			if (ConfigHelper.getBoolean("xp_boost_enable_level_threshold")
					&& event.getEntityPlayer().experienceLevel < ConfigHelper
							.getInt("xp_boost_enable_level_threshold_value"))
				return;
			long xp = (long) (ConfigHelper.getFloat("xp_multiplier") * event.getOrb().xpValue);
			if (xp > Integer.MAX_VALUE) {
				Beneficium.logger.warn("The multiplied exp exceeds the maximum value! Returning Integer.MAX_VALUE");
				xp = Integer.MAX_VALUE;
			}
			event.getOrb().xpValue = (int) xp;
			break;
		}
		case 2: {
			if (ConfigHelper.getBoolean("xp_boost_enable_level_threshold")
					&& event.getEntityPlayer().experienceLevel < ConfigHelper
							.getInt("xp_boost_enable_level_threshold_value"))
				return;
			long xp = (long) (ConfigHelper.getFloat("xp_multiplier_divine") * event.getOrb().xpValue);
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
