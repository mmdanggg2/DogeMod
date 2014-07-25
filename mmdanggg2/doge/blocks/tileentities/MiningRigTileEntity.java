package mmdanggg2.doge.blocks.tileentities;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.items.GPU;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class MiningRigTileEntity extends TileEntity implements IInventory {
	
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
	public void closeInventory() {}

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
	public String getInventoryName() {
		return "MiningRig";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
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
	public boolean hasCustomInventoryName() {
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
		DogeLogger.logInfo("Item is: " + item.getUnlocalizedName() + ", Slot is: " + i + ", Valid: " + valid);
		return stack.getItem() == Doge.dogecoin;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer arg0) {
		return arg0.getDistanceSq(xCoord + .5, yCoord + .5, zCoord + .5) <= 64;
	}

	@Override
	public void openInventory() {}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		items[i] = stack;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
		
	}
	
	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			for (int i = 0; i < getSizeInventory(); i++) {
				ItemStack stack = items[i];
				if (stack != null) {
					Item item = stack.getItem();
					if (item instanceof GPU) {
						GPU gpu = (GPU) item;
						if (worldObj.rand.nextInt(100) == 0) {
							boolean mined = gpu.attemptMine(stack, worldObj, 5);
							if (mined) {
								EntityItem coin = new EntityItem(worldObj);
								coin.setEntityItemStack(new ItemStack(Doge.dogecoin, 1));
								coin.setPosition(xCoord + .5, yCoord + 1.5, zCoord + .5);
								worldObj.spawnEntityInWorld(coin);
							}
							DogeLogger.logInfo("stack dmg = " + stack.getItemDamage());
							if (stack.getItemDamage() >= stack.getMaxDamage()) {
								DogeLogger.logInfo("setting stack to null");
								setInventorySlotContents(i, null);
							}
						}
					}
				}
			}
		}
	}
}
