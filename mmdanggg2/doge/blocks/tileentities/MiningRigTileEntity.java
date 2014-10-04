package mmdanggg2.doge.blocks.tileentities;

import java.util.Map;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.blocks.MiningRig;
import mmdanggg2.doge.items.GPU;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class MiningRigTileEntity extends TileEntity implements ISidedInventory {
	
	private ItemStack[] items;
	
	public MiningRigTileEntity() {
		items = new ItemStack[5];
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
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
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		NBTTagList items = compound.getTagList("Items");
		
		for (int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound) items.tagAt(i);
			int slot = item.getByte("Slot");
			
			if (slot >= 0 && slot < getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
	}
	
	@Override
	public void closeChest() {
	}
	
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
	public String getInvName() {
		return "MiningRig";
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
	public ItemStack getStackInSlotOnClosing(int arg0) {
		ItemStack stack = getStackInSlot(arg0);
		setInventorySlotContents(arg0, null);
		return stack;
	}
	
	@Override
	public boolean isInvNameLocalized() {
		return false;
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
	public boolean isUseableByPlayer(EntityPlayer arg0) {
		return arg0.getDistanceSq(xCoord + .5, yCoord + .5, zCoord + .5) <= 64;
	}
	
	@Override
	public void openChest() {
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		items[i] = stack;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit() && i != 4) {
			stack.stackSize = getInventoryStackLimit();
		}
		
	}
	
	@Override
	public void updateEntity() {
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
			
			int meta = getMeta();

			if (gpus > 0) {
				meta |= 4; // set 3rd bit to 1
				if (meta != getMeta()) {
					DogeLogger.logDebug("Setting meta to " + Integer.toBinaryString(meta) + ", " + meta);
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
				}
			}
			else {
				meta &= ~4; // set 3rd bit to 0
				if (meta != getMeta()) {
					DogeLogger.logDebug("Setting meta to " + Integer.toBinaryString(meta) + ", " + meta);
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
				}
			}
		}
	}
	
	public boolean isMining() {
		return (getMeta() & 4) != 0; // if 3rd bit is 1
	}
	
	private int getMeta() {
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		int[] inSlots = new int[] { 0, 1, 2, 3 };
		int[] outSlots = new int[] { 4 };
		
		Map<String, Integer> currRotation = MiningRig.getSidesOfCurrentRotation(getMeta());
		int front = currRotation.get("front");
		int right = currRotation.get("right");
		int back = currRotation.get("back");
		int left = currRotation.get("left");
		int top = currRotation.get("top");
		int bottom = currRotation.get("bottom");
		
		if (side == top || side == back || side == left) {
			return inSlots;
		}
		else if (side == bottom || side == front || side == right) {
			return outSlots;
		}
		return null;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		Map<String, Integer> currRotation = MiningRig.getSidesOfCurrentRotation(getMeta());
		int front = currRotation.get("front");
		int right = currRotation.get("right");
		int back = currRotation.get("back");
		int left = currRotation.get("left");
		int top = currRotation.get("top");
		int bottom = currRotation.get("bottom");
		
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
}
