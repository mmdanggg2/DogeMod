package mmdanggg2.doge.client.interfaces;

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
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 130));
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 72 + y * 18));
			}
		}
		
		for (int x = 0; x < 4; x++) {
			addSlotToContainer(new SlotGPU(miningRig, x, 8 + 35 * x, 20));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer arg0) {
		return miningRig.isUseableByPlayer(arg0);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer arg0, int arg1) {
		return null;
	}
	
}
