package mmdanggg2.doge.blocks.tileentities;

import java.util.Map;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.blocks.MiningRig;
import mmdanggg2.doge.items.GPU;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ITickable;

public class MiningRigTileEntity extends TileEntityLockable implements ISidedInventory, ITickable {
	
	private ItemStack[] items;
	private String mrCustomName;

	public MiningRigTileEntity() {
		items = new ItemStack[5];
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagList items = new NBTTagList();
		
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack stack = getStackInSlot(i);
			
			if (stack != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				stack.writeToNBT(item);
				items.appendTag(item);
			}
		}
		
		compound.setTag("Items", items);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		NBTTagList items = compound.getTagList("Items", 10);
		
		for (int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = items.getCompoundTagAt(i);
			int slot = item.getByte("Slot");
			
			if (slot >= 0 && slot < getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
	}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		ItemStack stack = getStackInSlot(slot);
		
		if (stack != null) {
			if (stack.stackSize <= count) {
				setInventorySlotContents(slot, null);
			}
			else {
				stack = stack.splitStack(count);
			}
		}
		
		return stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return items[i];
	}

	@Override
	public ItemStack removeStackFromSlot(int arg0) {
		ItemStack stack = getStackInSlot(arg0);
		setInventorySlotContents(arg0, null);
		return stack;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.mrCustomName : "container.furnace";
	}
	
	@Override
	public boolean hasCustomName()
    {
        return this.mrCustomName != null && this.mrCustomName.length() > 0;
    }
	
	public void setCustomInventoryName(String name)
    {
        this.mrCustomName = name;
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
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		items[i] = stack;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit() && i != 4) {
			stack.stackSize = getInventoryStackLimit();
		}
		
	}
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			int gpus = 0;
			
			for (int i = 0; i < getSizeInventory() - 1; i++) {
				ItemStack gpuStack = getStackInSlot(i);
				if (gpuStack != null && gpuStack.getItem() instanceof GPU) {
					gpus++;
					GPU gpu = (GPU) gpuStack.getItem();
					ItemStack dogeStack = getStackInSlot(4); 
					if (dogeStack == null || (dogeStack.getItem() == Doge.dogecoin && dogeStack.stackSize < 64)) {
						if (worldObj.rand.nextInt(DogeInfo.rigSpeed) == 0) {
							boolean mined = gpu.attemptMine(gpuStack, worldObj, DogeInfo.rigChance);
							DogeLogger.logDebug("stack dmg = " + gpuStack.getItemDamage() + "; mined = " + mined);
							if (mined) {
								if (dogeStack == null) {
									DogeLogger.logDebug("new coin stack!");
									dogeStack = new ItemStack(Doge.dogecoin, 1);
								}
								else {
									dogeStack.stackSize++;
									DogeLogger.logDebug("coin stack size = " + dogeStack.stackSize);
								}
								setInventorySlotContents(4, dogeStack);
							}
							if (gpuStack.getItemDamage() >= gpuStack.getMaxDamage()) {
								DogeLogger.logDebug("setting stack to null");
								setInventorySlotContents(i, null);
							}
						}
					}
				}
			}

			if (gpus > 0) {
				if (!isMining()) {
					DogeLogger.logDebug("Setting mining to True");
					MiningRig.setMining(true, worldObj, pos);
				}
			}
			else {
				if (isMining()) {
					DogeLogger.logDebug("Setting mining to False");
					MiningRig.setMining(false, worldObj, pos);
				}
			}
		}
	}
	
	public boolean isMining() {
		Block block = this.worldObj.getBlockState(this.pos).getBlock();
		if (block == Doge.miningRigOn) {
			return true;
		}
		else {
		return false;
		}
	}
	
	private IBlockState getState() {
		return worldObj.getBlockState(pos);
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
	public ITextComponent getDisplayName() {
		return null;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory,
			EntityPlayer playerIn) {
		return null;
	}

	@Override
	public String getGuiID() {
		return null;
	}

	@Override
	public void clear() {
	}
}
