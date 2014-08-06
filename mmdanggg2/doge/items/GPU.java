package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.util.DogeLogger;
import mmdanggg2.doge.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GPU extends Item {
	
	private int coinChance;
	private float speedStart;
	private float speedStep;
	private int coolRate;
	public GPU(int id) {
		super(id);
		this.maxStackSize = 1;
		this.setMaxDamage(20);
		this.setCreativeTab(Doge.dogeTab);
		this.setUnlocalizedName("gpu");
		this.setTextureName(DogeInfo.NAME.toLowerCase() + ":gpu");
		this.coinChance = DogeInfo.gpuChance;
		this.speedStart = DogeInfo.gpuSpeedStart;
		this.speedStep = DogeInfo.gpuSpeedStep;
		this.coolRate = DogeInfo.gpuCoolRate;
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
		return NBTHelper.getFloat(stack.stackTagCompound, "speed", speedStart);
	}
	
	@Override
	public boolean canHarvestBlock(Block block) {
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, int block,
			int xPos, int yPos, int zPos, EntityLivingBase entityLiving) {
		if (!world.isRemote) {
			
			boolean mined = attemptMine(stack, world, coinChance);
			
			if (mined) {
				EntityItem coin = new EntityItem(world);
				coin.setEntityItemStack(new ItemStack(Doge.dogecoin, 1));
				coin.setPosition(xPos + .5, yPos + .5, zPos + .5);
				world.spawnEntityInWorld(coin);
			}
			
			if (stack.getItemDamage() > stack.getMaxDamage()) {
				world.createExplosion(entityLiving, xPos, yPos, zPos, 2f, true);
				stack.stackSize = 0;
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
		
		if (world.rand.nextInt(10) == 0 && inHand) {
			randomDisplayTick(stack, world, entity);
		}
		
		if (!world.isRemote) {
			if (stack.stackTagCompound == null) {
				initTags(stack);
			}
			
			NBTTagCompound stackTag = stack.stackTagCompound;
			
			if (!inHand) {
				int tickCount = NBTHelper.getInt(stackTag, "tickCount", 0);
				
				if (tickCount >= coolRate) {
					tickCount = 0;
					if (stack.getItemDamage() > 0) {
						float speed = NBTHelper.getFloat(stackTag, "speed", speedStart);
						if (speed > speedStart) {
							speed = speed - speedStep;
							DogeLogger.logDebug("GPU Speed = " + speed);
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
		stack.stackTagCompound.setFloat("speed", speedStart);
		stack.stackTagCompound.setInteger("tickCount", 0);
	}
	
	public boolean attemptMine(ItemStack stack, World world, int chance) {
		boolean mined = false;
		
		stack.attemptDamageItem(1, world.rand);
		
		if (world.rand.nextInt(chance) == 0) {
			mined = true;
		}
		
		NBTTagCompound stackTag = stack.stackTagCompound;
		float speed = NBTHelper.getFloat(stackTag, "speed", speedStart);
		speed = speed + speedStep;
		DogeLogger.logDebug("GPU Speed = " + speed);
		stackTag.setFloat("speed", speed);
		
		return mined;
	}
	
	private void randomDisplayTick(ItemStack stack, World world, Entity entity) {
		if (entity instanceof EntityPlayer && world.isRemote) {
			EntityPlayer player = (EntityPlayer) entity;
			double x = player.posX;
			double y = player.posY;
			double z = player.posZ;
			float yaw = player.rotationYawHead;
			float pitch = player.rotationPitch;
			float depth;
			double offsetX;
			double offsetZ;
			double offsetY;
			
			if (Minecraft.getMinecraft().thePlayer == player && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
				yaw += 60;
				pitch += 30;
				depth = 0.3f;
				offsetX = (-MathHelper.sin(yaw / 180.0F * (float) Math.PI)) * depth;
				offsetZ = (MathHelper.cos(yaw / 180.0F * (float) Math.PI)) * depth;
				offsetY = (-MathHelper.sin(pitch / 180.0F * (float) Math.PI)) * depth;
			}
			else {
				yaw = player.renderYawOffset;
				yaw += 35;
				depth = 0.5f;
				offsetX = (-MathHelper.sin(yaw / 180.0F * (float) Math.PI)) * depth;
				offsetZ = (MathHelper.cos(yaw / 180.0F * (float) Math.PI)) * depth;
				offsetY = -0.6;
			}
			if (stack.getItemDamage() > 5) {
				world.spawnParticle("reddust", x + offsetX, y + offsetY, z + offsetZ, 0, 0, 0);
			}
			if (stack.getItemDamage() > 10) {
				world.spawnParticle("reddust", x + offsetX, y + offsetY, z + offsetZ, 0, 0, 0);
			}
			if (stack.getItemDamage() > 15) {
				world.spawnParticle("smoke", x + offsetX, y + offsetY, z + offsetZ, player.motionX, 0.0D, player.motionZ);
			}
			if (stack.getItemDamage() > 18) {
				world.spawnParticle("flame", x + offsetX, y + offsetY, z + offsetZ, player.motionX, 0.01D, player.motionZ);
			}
		}
	}
}
