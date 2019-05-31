package xiroc.beneficium.loot;

import java.util.Collection;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class LootPoolItem extends LootPool {

	private ItemStack item;
	private float chance;
	private RandomValueRange amount;

	public LootPoolItem(LootEntry[] lootEntriesIn, LootCondition[] poolConditionsIn, RandomValueRange rollsIn,
			RandomValueRange bonusRollsIn, ItemStack item, RandomValueRange amount, float chance, String name) {
		super(lootEntriesIn, poolConditionsIn, rollsIn, bonusRollsIn, name);
		this.item = item;
		this.chance = chance;
		this.amount = amount;
	}

	@Override
	public void generateLoot(Collection<ItemStack> stacks, Random rand, LootContext context) {
		if (rand.nextFloat() < chance) {
			item.setCount(amount.generateInt(rand));
			stacks.add(item);
		}
	}

}
