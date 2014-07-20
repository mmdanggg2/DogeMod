package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import mmdanggg2.doge.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class GPUPick extends Item {

	private int tickCount;

	public GPUPick() {
		this.maxStackSize = 1;
		this.setMaxDamage(100);
		this.setCreativeTab(Doge.dogeTab);
		this.setUnlocalizedName("gpuPick");
		this.setTextureName(BasicInfo.NAME.toLowerCase() + ":gpuPick");
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			if (stack.stackTagCompound == null) {
				initTags(stack);
			}
			stack.setItemDamage(0);
		}
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		return NBTHelper.getFloat(stack.stackTagCompound, "speed", 1);
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		return 3;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int xPos, int yPos, int zPos,
			EntityLivingBase entityLiving) {
		if (!world.isRemote) {
			NBTTagCompound stackTag = stack.stackTagCompound;
			float speed = NBTHelper.getFloat(stackTag, "speed", 1);
			speed = speed + 0.5f;
			stackTag.setFloat("speed", speed);
			stack.damageItem(1, entityLiving);
			if (entityLiving instanceof EntityPlayer) {
				((EntityPlayer) entityLiving).addChatMessage(new ChatComponentText("" + (int) speed));
			}
		}
		return super.onBlockDestroyed(stack, world, block, xPos, yPos, zPos, entityLiving);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			NBTTagCompound stackTag = stack.stackTagCompound;
			float speed = NBTHelper.getFloat(stackTag, "speed", 1);
			speed = 1;
			stackTag.setFloat("speed", speed);
			stack.setItemDamage(0);
		}
		return super.onItemRightClick(stack, world, player);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean inHand) {
		if (!world.isRemote) {
			if (stack.stackTagCompound == null) {
				initTags(stack);
			}
			
			NBTTagCompound stackTag = stack.stackTagCompound;
			
			if (tickCount >= 30) {
				tickCount = 0;
				if (stack.getItemDamage() > 0) {
					float speed = NBTHelper.getFloat(stackTag, "speed", 1);
					if (speed > 1) {
						speed = speed - 0.5f;
						stackTag.setFloat("speed", speed);
					}
					stack.damageItem(-1, (EntityLivingBase) entity);
				}
			}
			
			tickCount++;
		}
		super.onUpdate(stack, world, entity, itemSlot, inHand);
	}
	
	@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
		return true;
	}
	
	private void initTags(ItemStack stack) {
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setFloat("speed", 1.0f);
		stack.stackTagCompound.setFloat("speed", 1.0f);
	}
}
