package xiroc.beneficium.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import xiroc.beneficium.Beneficium;
import xiroc.beneficium.client.render.tileentity.TileEntityTreasureChestRenderer;
import xiroc.beneficium.common.block.BlockRegistry;
import xiroc.beneficium.common.block.tileentity.TileEntityTreasureChest;
import xiroc.beneficium.common.item.ItemArtifactPickaxe;
import xiroc.beneficium.common.item.ItemRegistry;
import xiroc.beneficium.common.loot.LootEntryDamaged;
import xiroc.beneficium.common.loot.LootPoolItem;
import xiroc.beneficium.common.network.PacketServerConfig;

public class EventManager {

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(BlockRegistry.treasure);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTreasureChest.class,
				new TileEntityTreasureChestRenderer());
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(ItemRegistry.treasure);
		event.getRegistry().register(ItemRegistry.talismanOfTheBenefactor);
		event.getRegistry().register(ItemRegistry.artifactPickaxe);
		event.getRegistry().register(ItemRegistry.guardiansCharm);
	}

	@SubscribeEvent
	public void registerRenderers(ModelRegistryEvent event) {
		ModelLoader.setCustomStateMapper(BlockRegistry.treasure, new StateMapperBase() {
			@Override
			public ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation("beneficium:treasure");
			}
		});

		ModelLoader.setCustomModelResourceLocation(ItemRegistry.talismanOfTheBenefactor, 0,
				new ModelResourceLocation(Beneficium.locate("talisman"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemRegistry.talismanOfTheBenefactor, 1,
				new ModelResourceLocation(Beneficium.locate("talisman"), "inventory"));

		ModelLoader.setCustomModelResourceLocation(ItemRegistry.artifactPickaxe, 0,
				new ModelResourceLocation(Beneficium.locate("artifact_pickaxe"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemRegistry.artifactPickaxe, 1,
				new ModelResourceLocation(Beneficium.locate("artifact_pickaxe"), "inventory"));

		ModelLoader.setCustomModelResourceLocation(ItemRegistry.guardiansCharm, 0,
				new ModelResourceLocation(Beneficium.locate("guardians_charm"), "inventory"));

		ModelLoader.setCustomModelResourceLocation(ItemRegistry.treasure, 0,
				new ModelResourceLocation(Beneficium.locate("treasure"), "inventory"));
	}

	@SubscribeEvent
	public void onModelBake(ModelBakeEvent event) {
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
					new LootEntry[] { new LootEntryDamaged(ItemRegistry.talismanOfTheBenefactor, 1, 1,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:divine_talisman") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(ItemRegistry.talismanOfTheBenefactor, 1, 1), new RandomValueRange(1),
					ConfigHelper.getFloat("divine_talisman_loot_chance"), "beneficium:talisman");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/stronghold_crossing": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootPoolItem pool = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(ItemRegistry.talismanOfTheBenefactor, 1, 1,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:divine_talisman") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(ItemRegistry.talismanOfTheBenefactor, 1, 1), new RandomValueRange(1),
					ConfigHelper.getFloat("divine_talisman_loot_chance"), "beneficium:talisman");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/simple_dungeon": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootPoolItem pool = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(ItemRegistry.talismanOfTheBenefactor, 1, 1,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:divine_talisman") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(ItemRegistry.talismanOfTheBenefactor, 1, 1), new RandomValueRange(1),
					ConfigHelper.getFloat("divine_talisman_loot_chance"), "beneficium:talisman");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/nether_bridge": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootPoolItem pool = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(ItemRegistry.talismanOfTheBenefactor, 1, 1,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:divine_talisman") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(ItemRegistry.talismanOfTheBenefactor, 1, 1), new RandomValueRange(1),
					ConfigHelper.getFloat("divine_talisman_loot_chance"), "beneficium:talisman");
			event.getTable().addPool(pool);
			break;
		}
		case "minecraft:chests/jungle_temple": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootPoolItem pool = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(ItemRegistry.talismanOfTheBenefactor, 1, 1,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:divine_talisman") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(ItemRegistry.talismanOfTheBenefactor, 1, 1), new RandomValueRange(1),
					ConfigHelper.getFloat("divine_talisman_loot_chance"), "beneficium:talisman");
			event.getTable().addPool(pool);
			LootPoolItem poolArtifactPickaxe = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(ItemRegistry.artifactPickaxe, 1, 0, new LootFunction[0],
							new LootCondition[0], 1, "beneficium:artifact_pickaxe") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(ItemRegistry.artifactPickaxe), new RandomValueRange(1),
					ConfigHelper.getFloat("artifact_pickaxe_loot_chance"), "beneficium:artifact_pickaxe");
			event.getTable().addPool(poolArtifactPickaxe);
			break;
		}
		case "minecraft:chests/stronghold_library": {
			Beneficium.logger.info("Modifying LootTable: " + event.getName().toString());
			LootPoolItem pool = new LootPoolItem(
					new LootEntry[] { new LootEntryDamaged(ItemRegistry.talismanOfTheBenefactor, 1, 1,
							new LootFunction[0], new LootCondition[0], 1, "beneficium:divine_talisman") },
					new LootCondition[0], new RandomValueRange(0), new RandomValueRange(0),
					new ItemStack(ItemRegistry.talismanOfTheBenefactor, 1, 1), new RandomValueRange(1),
					ConfigHelper.getFloat("divine_talisman_loot_chance"), "beneficium:talisman");
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
					&& handler.getStackInSlot(a).getItem() == ItemRegistry.talismanOfTheBenefactor) {
				int meta = handler.getStackInSlot(a).getItemDamage() + 1;
				if (meta > level)
					level = meta;
			}
		}
		return level;
	}

}
