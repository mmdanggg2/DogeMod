package mmdanggg2.doge.client.gui;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.tileentitiy.MiningRigTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MiningRigContainer extends Container {
	
	public MiningRigTileEntity miningRig;
	
	public MiningRigContainer(InventoryPlayer invPlayer, MiningRigTileEntity miningRig) {
		this.miningRig = miningRig;
		
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 109));
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 51 + y * 18));
			}
		}
		
		for (int x = 0; x < 4; x++) {
			addSlotToContainer(new SlotGPU(miningRig, x, 26 + 36 * x, 20, false));
		}
		
		addSlotToContainer(new SlotGPU(miningRig, 4, 26 + 36 * 4, 20, true));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return miningRig.isUsableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = getSlot(i);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			itemstack = slotStack.copy();
			
			if (i >= 36) {
				if (!mergeItemStack(slotStack, 0, 35, false)) {
					return ItemStack.EMPTY;
				}
			}
			else if (slotStack.getItem() != Doge.gpu || !mergeItemStack(slotStack, 36, 36 + miningRig.getSizeInventory() - 1, false)) {
				return ItemStack.EMPTY;
			}
			
			if (slotStack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			}
			else {
				slot.onSlotChanged();
			}
			
			if (slotStack.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}
			
			slot.onTake(player, slotStack);
		}
		return itemstack;
	}
	
}
