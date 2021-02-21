package mmdanggg2.doge.tileentitiy;

import java.util.HashSet;
import java.util.Map;

import mmdanggg2.doge.block.MiningRig;
import mmdanggg2.doge.config.DogeConfig;
import mmdanggg2.doge.container.MiningRigContainer;
import mmdanggg2.doge.init.DogeBlocks;
import mmdanggg2.doge.init.DogeItems;
import mmdanggg2.doge.init.DogeTileEntityTypes;
import mmdanggg2.doge.item.GPU;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class MiningRigTileEntity extends LockableTileEntity implements ISidedInventory, ITickableTileEntity {
	
	private NonNullList<ItemStack> items = NonNullList.<ItemStack>withSize(5, ItemStack.EMPTY);

	public MiningRigTileEntity() {
		super(DogeTileEntityTypes.MINING_RIG.get());
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		
		ItemStackHelper.saveAllItems(compound, this.items);
		
		return compound;
	}
	
	@Override
	public void read(BlockState cachedBlockState, CompoundNBT compound) {
		super.read(cachedBlockState, compound);
		
		items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, items);
	}

	@Override
	public void closeInventory(PlayerEntity player) {}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		ItemStack stack = getStackInSlot(slot);
		
		if (!stack.isEmpty()) {
			if (stack.getCount() <= count) {
				setInventorySlotContents(slot, ItemStack.EMPTY);
			}
			else {
				stack = stack.split(count);
			}
		}
		
		return stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public int getSizeInventory() {
		return items.size();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return items.get(i);
	}

	@Override
	public ItemStack removeStackFromSlot(int arg0) {
		ItemStack stack = getStackInSlot(arg0);
		setInventorySlotContents(arg0, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		boolean valid = false;
		Item item = stack.getItem();
		if (i == 4) {
			valid = item == DogeItems.DOGECOIN.get();
		}
		else {
			valid = item == DogeItems.GPU.get();
		}
		// DogeLogger.logInfo("Item is: " + item.getUnlocalizedName() + ", Slot is: " + i + ", Valid: " + valid);
		return valid;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return player.getDistanceSq(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5) <= 64;
	}

	@Override
	public void openInventory(PlayerEntity player) {}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		items.set(i, stack);
		
		if (stack.getCount() > getInventoryStackLimit()) {
			stack.setCount(getInventoryStackLimit());
		}
		
	}
	
	@Override
	public void tick() {
		if (!world.isRemote) {
			boolean dirty = false;
			for (ItemStack gpuStack : getGPUs()) {
				GPU gpu = (GPU) gpuStack.getItem();
				ItemStack dogeStack = getStackInSlot(4); 
				if (dogeStack.isEmpty() || (dogeStack.getItem() == DogeItems.DOGECOIN.get() && dogeStack.getCount() < dogeStack.getMaxStackSize())) {
					if (world.rand.nextInt(DogeConfig.rigSpeed) == 0) {
						boolean mined = gpu.attemptMine(gpuStack, world, DogeConfig.rigChance);
						DogeLogger.logDebug("GPU dmg = " + gpuStack.getDamage() + "; mined = " + mined);
						if (mined) {
							if (dogeStack.isEmpty()) {
								DogeLogger.logDebug("New coin stack!");
								dogeStack = new ItemStack(DogeItems.DOGECOIN.get(), 1);
							}
							else {
								dogeStack.grow(1);
								DogeLogger.logDebug("Coin stack size = " + dogeStack.getCount());
							}
							setInventorySlotContents(4, dogeStack);
							dirty = true;
						}
						if (gpuStack.getDamage() >= gpuStack.getMaxDamage()) {
							DogeLogger.logDebug("Setting GPU stack to empty");
							gpuStack.setCount(0);
							//setInventorySlotContents(i, ItemStack.EMPTY);
							dirty = true;
						}
					}
				}
			}

			if (isMining()) {
				if (!getState().get(MiningRig.MINING)) {
					DogeLogger.logDebug("Setting mining state to True");
					MiningRig.setMining(true, world, pos);
					dirty = true;
				}
			} else {
				if (getState().get(MiningRig.MINING)) {
					DogeLogger.logDebug("Setting mining state to False");
					MiningRig.setMining(false, world, pos);
					dirty = true;
				}
			}
			if (dirty) {
				markDirty();
			}
		}
	}
	
	private NonNullList<ItemStack> getGPUs() {
		NonNullList<ItemStack> gpus = NonNullList.<ItemStack>create();
		for (ItemStack gpuStack : items) {
			if (!gpuStack.isEmpty() && gpuStack.getItem() instanceof GPU) {
				gpus.add(gpuStack);
			}
		}
		return gpus;
	}
	
	public boolean isMining() {
		ItemStack dogeStack = getStackInSlot(4);
		return getGPUs().size() > 0 && dogeStack.getCount() < dogeStack.getMaxStackSize();
	}
	
	private BlockState getState() {
		return world.getBlockState(pos);
	}
	
	@Override
	public int[] getSlotsForFace(Direction side) {
		int[] inSlots = new int[] { 0, 1, 2, 3 };
		int[] outSlots = new int[] { 4 };
		
		Map<String, Direction> currRotation = MiningRig.getSidesOfCurrentRotation(getState());
		Direction front = currRotation.get("front");
		Direction right = currRotation.get("right");
		Direction back = currRotation.get("back");
		Direction left = currRotation.get("left");
		Direction top = currRotation.get("top");
		Direction bottom = currRotation.get("bottom");
		
		if (side == top || side == back || side == left) {
			return inSlots;
		}
		else if (side == bottom || side == front || side == right) {
			return outSlots;
		}
		return null;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, Direction side) {
		return isItemValidForSlot(slot, stack);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack stack, Direction side) {
		Map<String, Direction> currRotation = MiningRig.getSidesOfCurrentRotation(getState());
		Direction front = currRotation.get("front");
		Direction right = currRotation.get("right");
		Direction back = currRotation.get("back");
		Direction left = currRotation.get("left");
		Direction top = currRotation.get("top");
		Direction bottom = currRotation.get("bottom");
		
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
	public void clear() {
		items.clear();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent(DogeBlocks.MINING_RIG.get().getTranslationKey());
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new MiningRigContainer(id, player, this);
	}

	LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers = 
			SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (!this.removed && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (side == Direction.UP)
				return handlers[0].cast();
			else if (side == Direction.DOWN)
				return handlers[1].cast();
			else
				return handlers[2].cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void remove() {
		super.remove();
		for (int x = 0; x < handlers.length; x++)
			handlers[x].invalidate();
	}
}
