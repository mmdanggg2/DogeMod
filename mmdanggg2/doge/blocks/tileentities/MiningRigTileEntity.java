package mmdanggg2.doge.blocks.tileentities;

import mmdanggg2.doge.Doge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class MiningRigTileEntity extends TileEntity implements IInventory {
	
	private ItemStack[] items;
	
	public MiningRigTileEntity() {
		items = new ItemStack[4];
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
	public ItemStack decrStackSize(int arg0, int count) {
		ItemStack stack = getStackInSlot(arg0);
		
		if (stack != null) {
			if (stack.stackSize <= count) {
				setInventorySlotContents(arg0, null);
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
	public boolean isItemValidForSlot(int arg0, ItemStack arg1) {
		return arg1.getItem() == Doge.dogecoin;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer arg0) {
		return arg0.getDistanceSq(xCoord + .5, yCoord + .5, zCoord + .5) <= 64;
	}
	
	@Override
	public void openChest() {
	}
	
	@Override
	public void setInventorySlotContents(int arg0, ItemStack stack) {
		items[arg0] = stack;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
		
	}
}
