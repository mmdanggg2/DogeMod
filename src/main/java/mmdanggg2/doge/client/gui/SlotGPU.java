package mmdanggg2.doge.client.gui;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.init.DogeItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotGPU extends Slot {
	
	private boolean coinSlot = false;

	public SlotGPU(IInventory inventory, int id, int x, int y, boolean coinSlot) {
		super(inventory, id, x, y);
		this.coinSlot = coinSlot;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		if (this.coinSlot) {
			return false;
		}
		else {
			return stack.getItem() == DogeItems.DOGECOIN.get();
		}
	}
	
}
