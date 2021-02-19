package mmdanggg2.doge.container;

import java.util.Objects;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.client.gui.SlotGPU;
import mmdanggg2.doge.init.DogeContainerTypes;
import mmdanggg2.doge.init.DogeItems;
import mmdanggg2.doge.tileentitiy.MiningRigTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.SlotItemHandler;

public class MiningRigContainer extends Container {
	
	public MiningRigTileEntity miningRig;
	
	public MiningRigContainer(int windowId, PlayerInventory playerInv, PacketBuffer data) {
		this(windowId, playerInv, getTileEntity(playerInv, data));
	}
	
	public MiningRigContainer(int windowId, PlayerInventory playerInv, MiningRigTileEntity miningRig) {
		super(DogeContainerTypes.MINING_RIG.get(), windowId);
		this.miningRig = miningRig;
		
		for (int x = 0; x < 9; x++) {
			addSlot(new Slot(playerInv, x, 8 + 18 * x, 109));
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlot(new Slot(playerInv, x + y * 9 + 9, 8 + 18 * x, 51 + y * 18));
			}
		}
		
		for (int x = 0; x < 4; x++) {
			addSlot(new SlotGPU(miningRig, x, 26 + 36 * x, 20, false));
		}
		
		addSlot(new SlotGPU(miningRig, 4, 26 + 36 * 4, 20, true));
	}

	private static MiningRigTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
		Objects.requireNonNull(playerInv, "playerInventory cannot be null!");
		Objects.requireNonNull(data, "data cannot be null!");
		final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof MiningRigTileEntity)
			return (MiningRigTileEntity) tileAtPos;
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return miningRig.isUsableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int i) {
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
			else if (slotStack.getItem() != DogeItems.GPU.get() || !mergeItemStack(slotStack, 36, 36 + miningRig.getSizeInventory() - 1, false)) {
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
