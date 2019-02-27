package mmdanggg2.doge.client.interfaces;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.blocks.tileentities.MiningRigTileEntity;
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
		Slot slot = getSlot(i);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			ItemStack result = stack.copy();
			
			if (i >= 36) {
				if (!mergeItemStack(stack, 0, 36, false)) {
					return null;
				}
			}
			else if (stack.getItem() != Doge.gpu || !mergeItemStack(stack, 36, 36 + miningRig.getSizeInventory() - 1, false)) {
				return null;
			}
			
			if (stack.isEmpty()) {
				slot.putStack(null);
			}
			else {
				slot.onSlotChanged();
			}
			
			slot.onTake(player, stack);
			
			return result;
		}
		return null;
	}
	
}
