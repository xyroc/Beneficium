package xiroc.beneficium.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import xiroc.beneficium.Beneficium;

public class ContainerTreasureChest extends Container {

	public final IInventory chestInventory;

	public ContainerTreasureChest(IInventory playerInventory, IInventory chestInventory, EntityPlayer player) {
		this.chestInventory = chestInventory;
		chestInventory.openInventory(player);
		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(chestInventory, k + j * 9, 8 + k * 18, 17 + j * 18));
			}
		}
		for (int l = 0; l < 3; ++l) {
			for (int j1 = 0; j1 < 9; ++j1) {
				this.addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 85 + l * 18));
			}
		}
		for (int i1 = 0; i1 < 9; ++i1) {
			this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 143));
		}
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.chestInventory.isUsableByPlayer(playerIn);
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 27) {
				if (!this.mergeItemStack(itemstack1, 27, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 27, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		this.chestInventory.closeInventory(playerIn);
	}

	public IInventory getChestInventory() {
		return this.chestInventory;
	}

}
