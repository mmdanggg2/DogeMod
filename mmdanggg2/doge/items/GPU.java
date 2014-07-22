package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import mmdanggg2.doge.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GPU extends Item {
	
	public GPU(int id) {
		super(id);
		this.maxStackSize = 1;
		this.setMaxDamage(20);
		this.setCreativeTab(Doge.dogeTab);
		this.setUnlocalizedName("gpu");
		this.setTextureName(BasicInfo.NAME.toLowerCase() + ":gpu");
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
	public float getStrVsBlock(ItemStack stack, Block block, int meta) {
		return NBTHelper.getFloat(stack.stackTagCompound, "speed", 1);
	}
	
	@Override
	public boolean canHarvestBlock(Block block) {
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, int block,
			int xPos, int yPos, int zPos,
			EntityLivingBase entityLiving) {
		if (!world.isRemote) {
			
			if (world.rand.nextInt((stack.getMaxDamage() + 1) / 2) == 0) {
				EntityItem coin = new EntityItem(world);
				coin.setEntityItemStack(new ItemStack(Doge.dogecoin, 1));
				coin.setPosition(xPos + .5, yPos + .5, zPos + .5);
				world.spawnEntityInWorld(coin);
			}
			
			NBTTagCompound stackTag = stack.stackTagCompound;
			float speed = NBTHelper.getFloat(stackTag, "speed", 1);
			speed = speed + 2f;
			stackTag.setFloat("speed", speed);
			stack.damageItem(1, entityLiving);
			if (stack.getItemDamage() >= stack.getMaxDamage()) {
				world.createExplosion(entityLiving, xPos, yPos, zPos, 2f, true);
				stack.damageItem(1, entityLiving);
			}
		}
		return super.onBlockDestroyed(stack, world, block, xPos, yPos, zPos, entityLiving);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return super.onItemRightClick(stack, world, player);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean inHand) {
		if (!world.isRemote) {
			if (stack.stackTagCompound == null) {
				initTags(stack);
			}
			
			NBTTagCompound stackTag = stack.stackTagCompound;
			
			if (!inHand) {
				int tickCount = NBTHelper.getInt(stackTag, "tickCount", 0);
				
				if (tickCount >= 30) {
					tickCount = 0;
					if (stack.getItemDamage() > 0) {
						float speed = NBTHelper.getFloat(stackTag, "speed", 1);
						if (speed > 1) {
							speed = speed - 1f;
							stackTag.setFloat("speed", speed);
						}
						stack.damageItem(-1, (EntityLivingBase) entity);
					}
				}
				tickCount++;
				stackTag.setInteger("tickCount", tickCount);
			}
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
		stack.stackTagCompound.setInteger("tickCount", 0);
	}
}
