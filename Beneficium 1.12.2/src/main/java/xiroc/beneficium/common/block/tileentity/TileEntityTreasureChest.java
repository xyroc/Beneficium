package xiroc.beneficium.common.block.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xiroc.beneficium.Beneficium;
import xiroc.beneficium.common.advancement.trigger.TriggerRegistry;
import xiroc.beneficium.common.block.BlockTreasureChest;
import xiroc.beneficium.common.container.ContainerTreasureChest;
import xiroc.beneficium.common.util.GuiHandler;

public class TileEntityTreasureChest extends TileEntityLockableLoot implements IInventory, ITickable {

	private NonNullList<ItemStack> chestContents = NonNullList.<ItemStack>withSize(this.getSizeInventory(),
			ItemStack.EMPTY);
	public float lidAngle;
	public float prevLidAngle;
	public int numPlayersUsing;
	private int ticksSinceSync;

	public TileEntityTreasureChest() {
		// Emptiness...
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.chestContents) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static void registerFixesChest(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY,
				new ItemStackDataLists(TileEntityTreasureChest.class, new String[] { "Items" }));
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.chestContents = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, chestContents);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, this.chestContents);
		return compound;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	private boolean isChestAt(BlockPos posIn) {
		if (this.world == null) {
			return false;
		} else {
			Block block = this.world.getBlockState(posIn).getBlock();
			return block instanceof BlockTreasureChest;
		}
	}

	@Override
	public void update() {
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();
		++this.ticksSinceSync;

		if (!this.world.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0) {
			this.numPlayersUsing = 0;
			float f = 5.0F;

			for (EntityPlayer entityplayer : this.world.getEntitiesWithinAABB(EntityPlayer.class,
					new AxisAlignedBB((double) ((float) i - 5.0F), (double) ((float) j - 5.0F),
							(double) ((float) k - 5.0F), (double) ((float) (i + 1) + 5.0F),
							(double) ((float) (j + 1) + 5.0F), (double) ((float) (k + 1) + 5.0F)))) {
				if (entityplayer.openContainer instanceof ContainerTreasureChest) {
					IInventory iinventory = ((ContainerTreasureChest) entityplayer.openContainer).getChestInventory();

					if (iinventory == this) {
						++this.numPlayersUsing;
					}
				}
			}
		}

		this.prevLidAngle = this.lidAngle;
		float f1 = 0.1F;

		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
			double d1 = (double) i + 0.5D;
			double d2 = (double) k + 0.5D;

			this.world.playSound((EntityPlayer) null, d1, (double) j + 0.5D, d2, SoundEvents.BLOCK_CHEST_OPEN,
					SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
			float f2 = this.lidAngle;

			if (this.numPlayersUsing > 0) {
				this.lidAngle += 0.1F;
			} else {
				this.lidAngle -= 0.1F;
			}

			if (this.lidAngle > 1.0F) {
				this.lidAngle = 1.0F;
			}

			float f3 = 0.5F;

			if (this.lidAngle < 0.5F && f2 >= 0.5F) {
				double d3 = (double) i + 0.5D;
				double d0 = (double) k + 0.5D;
				this.world.playSound((EntityPlayer) null, d3, (double) j + 0.5D, d0, SoundEvents.BLOCK_CHEST_CLOSE,
						SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
			}
			if (this.lidAngle < 0.0F) {
				this.lidAngle = 0.0F;
			}
		}
	}

	@Override
	public boolean receiveClientEvent(int id, int type) {
		if (id == 1) {
			this.numPlayersUsing = type;
			return true;
		} else {
			return super.receiveClientEvent(id, type);
		}
	}

	@Override
	public void openInventory(EntityPlayer player) {
		if (!player.world.isRemote)
			TriggerRegistry.TRIGGER_FIND_TREASURE.trigger((EntityPlayerMP) player);
		if (!player.isSpectator()) {
			if (this.numPlayersUsing < 0)
				this.numPlayersUsing = 0;
			++this.numPlayersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
		}
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		if (!player.isSpectator() && this.getBlockType() instanceof BlockTreasureChest) {
			--this.numPlayersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		this.updateContainingBlockInfo();
	}

	public ContainerTreasureChest createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerTreasureChest(playerInventory, this, playerIn);
	}

	public NonNullList<ItemStack> getItems() {
		return this.chestContents;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	/*@Override
	public ItemStack getStackInSlot(int index) {
		return chestContents.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = getStackInSlot(index);
		ItemStack split = stack.splitStack(count);
		setInventorySlotContents(index, stack);
		return split;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = getStackInSlot(index);
		setInventorySlotContents(index, ItemStack.EMPTY);
		Beneficium.logger.info(stack);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		chestContents.set(index, stack);
	}*/

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return super.isUsableByPlayer(player);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index >= 0 && index < getSizeInventory();
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		super.clear();
	}

	@Override
	public String getName() {
		return "Treasure Chest";
	}

	@Override
	public String getGuiID() {
		return "beneficium:treasure_chest";
	}

}
