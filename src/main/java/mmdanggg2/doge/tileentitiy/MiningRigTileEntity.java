package mmdanggg2.doge.tileentitiy;

import java.util.Map;

import javax.annotation.Nonnull;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.block.MiningRig;
import mmdanggg2.doge.item.GPU;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

public class MiningRigTileEntity extends LockableTileEntity implements ISidedInventory {
	
	private NonNullList<ItemStack> items = NonNullList.<ItemStack>withSize(5, ItemStack.EMPTY);
	private String mrCustomName;

	public MiningRigTileEntity() {
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
        ItemStackHelper.saveAllItems(compound, this.items);
		
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		ItemStackHelper.loadAllItems(compound, items);
	}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		ItemStack stack = getStackInSlot(slot);
		
		if (!stack.isEmpty()) {
			if (stack.getCount() <= count) {
				setInventorySlotContents(slot, ItemStack.EMPTY);
			}
			else {
				stack = stack.splitStack(count);
			}
		}
		
		return stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public int getSizeInventory() {
		return items.size();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return items.get(i);
	}

	@Override
	public ItemStack removeStackFromSlot(int arg0) {
		ItemStack stack = getStackInSlot(arg0);
		setInventorySlotContents(arg0, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.mrCustomName : "tile.mining_rig.name";
	}
	
	@Override
	public boolean hasCustomName()
    {
        return this.mrCustomName != null && this.mrCustomName.length() > 0;
    }
	
	public void setCustomInventoryName(String name)
    {
        //this.mrCustomName = name;
    }

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		boolean valid = false;
		Item item = stack.getItem();
		if (i == 4) {
			valid = (item == Doge.dogecoin);
		}
		else {
			valid = (item == Doge.gpu);
		}
		// DogeLogger.logInfo("Item is: " + item.getUnlocalizedName() + ", Slot is: " + i + ", Valid: " + valid);
		return valid;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		items.set(i, stack);
		
		if (stack.getCount() > getInventoryStackLimit()) {
			stack.setCount(getInventoryStackLimit());
		}
		
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			
			for (ItemStack gpuStack : getGPUs()) {
				GPU gpu = (GPU) gpuStack.getItem();
				ItemStack dogeStack = getStackInSlot(4); 
				if (dogeStack.isEmpty() || (dogeStack.getItem() == Doge.dogecoin && dogeStack.getCount() < dogeStack.getMaxStackSize())) {
					if (world.rand.nextInt(DogeInfo.rigSpeed) == 0) {
						boolean mined = gpu.attemptMine(gpuStack, world, DogeInfo.rigChance);
						DogeLogger.logDebug("GPU dmg = " + gpuStack.getItemDamage() + "; mined = " + mined);
						if (mined) {
							if (dogeStack.isEmpty()) {
								DogeLogger.logDebug("New coin stack!");
								dogeStack = new ItemStack(Doge.dogecoin, 1);
							}
							else {
								dogeStack.grow(1);
								DogeLogger.logDebug("Coin stack size = " + dogeStack.getCount());
							}
							setInventorySlotContents(4, dogeStack);
						}
						if (gpuStack.getItemDamage() >= gpuStack.getMaxDamage()) {
							DogeLogger.logDebug("Setting GPU stack to empty");
							gpuStack.setCount(0);
							//setInventorySlotContents(i, ItemStack.EMPTY);
						}
					}
				}
			}

			if (isMining()) {
				if (!getState().getValue(MiningRig.MINING)) {
					DogeLogger.logDebug("Setting mining state to True");
					MiningRig.setMining(true, world, pos);
				}
			}
			else {
				if (getState().getValue(MiningRig.MINING)) {
					DogeLogger.logDebug("Setting mining state to False");
					MiningRig.setMining(false, world, pos);
				}
			}
		}
	}
	
	private NonNullList<ItemStack> getGPUs() {
		NonNullList<ItemStack> gpus = NonNullList.<ItemStack>create();
		for (ItemStack gpuStack : items) {
			if (!gpuStack.isEmpty() && gpuStack.getItem() instanceof GPU) {
				gpus.add(gpuStack);
			}
		}
		return gpus;
	}
	
	public boolean isMining() {
		return getGPUs().size() > 0;
	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		int[] inSlots = new int[] { 0, 1, 2, 3 };
		int[] outSlots = new int[] { 4 };
		
		Map<String, EnumFacing> currRotation = MiningRig.getSidesOfCurrentRotation(getState());
		EnumFacing front = currRotation.get("front");
		EnumFacing right = currRotation.get("right");
		EnumFacing back = currRotation.get("back");
		EnumFacing left = currRotation.get("left");
		EnumFacing top = currRotation.get("top");
		EnumFacing bottom = currRotation.get("bottom");
		
		if (side == top || side == back || side == left) {
			return inSlots;
		}
		else if (side == bottom || side == front || side == right) {
			return outSlots;
		}
		return null;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) {
		return isItemValidForSlot(slot, stack);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side) {
		Map<String, EnumFacing> currRotation = MiningRig.getSidesOfCurrentRotation(getState());
		EnumFacing front = currRotation.get("front");
		EnumFacing right = currRotation.get("right");
		EnumFacing back = currRotation.get("back");
		EnumFacing left = currRotation.get("left");
		EnumFacing top = currRotation.get("top");
		EnumFacing bottom = currRotation.get("bottom");
		
		if (side == top || side == back || side == left) {
			DogeLogger.logDebug("Can't Extract Item");
			return false;
		}
		else if (side == bottom || side == front || side == right) {
			DogeLogger.logDebug("Can Extract Item");
			return true;
		}

		return false;
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
	public Container createContainer(InventoryPlayer playerInventory,
			EntityPlayer playerIn) {
		return null;
	}

	@Override
	public String getGuiID() {
		return "doge:mining_rig";
	}

	@Override
	public void clear() {
		items.clear();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.items)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
	}
}
