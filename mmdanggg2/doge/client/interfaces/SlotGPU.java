package mmdanggg2.doge.client.interfaces;

import mmdanggg2.doge.Doge;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotGPU extends Slot {
	
	public SlotGPU(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() == Doge.gpu;
	}
	
}
