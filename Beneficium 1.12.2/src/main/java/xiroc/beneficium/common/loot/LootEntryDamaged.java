package xiroc.beneficium.common.loot;

import java.util.Collection;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunction;

public class LootEntryDamaged extends LootEntryItem {

	public int damage;

	public LootEntryDamaged(Item itemIn, int weightIn, int qualityIn, LootFunction[] functionsIn,
			LootCondition[] conditionsIn, int damage, String entryName) {
		super(itemIn, weightIn, qualityIn, functionsIn, conditionsIn, entryName);
		this.damage = damage;
	}

	@Override
	public void addLoot(Collection<ItemStack> stacks, Random rand, LootContext context) {
		ItemStack itemstack = new ItemStack(this.item);
		itemstack.setItemDamage(damage);
		
		for (LootFunction lootfunction : this.functions) {
			if (LootConditionManager.testAllConditions(lootfunction.getConditions(), rand, context)) {
				itemstack = lootfunction.apply(itemstack, rand, context);
			}
		}
		
		if (!itemstack.isEmpty()) {
			if (itemstack.getCount() < this.item.getItemStackLimit(itemstack)) {
				stacks.add(itemstack);
			} else {
				int i = itemstack.getCount();

				while (i > 0) {
					ItemStack itemstack1 = itemstack.copy();
					itemstack1.setCount(Math.min(itemstack.getMaxStackSize(), i));
					i -= itemstack1.getCount();
					stacks.add(itemstack1);
				}
			}
		}

	}
}
