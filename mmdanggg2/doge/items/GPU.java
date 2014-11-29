package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.util.DogeLogger;
import mmdanggg2.doge.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GPU extends Item {

	private int coinChance;
	private float speedStart;
	private float speedStep;
	private int coolRate;
	
	public GPU() {
		this.maxStackSize = 1;
		this.setMaxDamage(20);
		this.setCreativeTab(Doge.dogeTab);
		this.setUnlocalizedName("gpu");
		this.coinChance = DogeInfo.gpuChance;
		this.speedStart = DogeInfo.gpuSpeedStart;
		this.speedStep = DogeInfo.gpuSpeedStep;
		this.coolRate = DogeInfo.gpuCoolRate;
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			if (!stack.hasTagCompound()) {
				initTags(stack);
			}
			stack.setItemDamage(0);
		}
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state) {
		return NBTHelper.getFloat(stack.getTagCompound(), "speed", speedStart);
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		return 3;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, BlockPos pos, EntityLivingBase entityLiving) {
		if (!world.isRemote) {
			int xPos = pos.getX();
			int yPos = pos.getY();
			int zPos = pos.getZ();
			
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
		return super.onBlockDestroyed(stack, world, block, pos, entityLiving);
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
			if (!stack.hasTagCompound()) {
				initTags(stack);
			}
			
			NBTTagCompound stackTag = stack.getTagCompound();
			
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
						stack.attemptDamageItem(-1, world.rand);
					}
				}
				tickCount++;
				stackTag.setInteger("tickCount", tickCount);
			}
		}
		super.onUpdate(stack, world, entity, itemSlot, inHand);
	}

	@Override
	public boolean canHarvestBlock(Block par1Block) {
		return true;
	}
	
	private void initTags(ItemStack stack) {
		NBTTagCompound stackTag = new NBTTagCompound();
		stackTag.setFloat("speed", speedStart);
		stackTag.setInteger("tickCount", 0);
		stack.setTagCompound(stackTag);
	}
	
	public boolean attemptMine(ItemStack stack, World world, int chance) {
		boolean mined = false;
		
		if (!stack.hasTagCompound()) {
			initTags(stack);
		}
		
		stack.attemptDamageItem(1, world.rand);

		if (world.rand.nextInt(chance) == 0) {
			mined = true;
		}
		
		NBTTagCompound stackTag = stack.getTagCompound();
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
				world.spawnParticle(EnumParticleTypes.REDSTONE, x + offsetX, y + offsetY, z + offsetZ, 0, 0, 0);
			}
			if (stack.getItemDamage() > 10) {
				world.spawnParticle(EnumParticleTypes.REDSTONE, x + offsetX, y + offsetY, z + offsetZ, 0, 0, 0);
			}
			if (stack.getItemDamage() > 15) {
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + offsetX, y + offsetY, z + offsetZ, player.motionX, 0.0D, player.motionZ);
			}
			if (stack.getItemDamage() > 18) {
				world.spawnParticle(EnumParticleTypes.FLAME, x + offsetX, y + offsetY, z + offsetZ, player.motionX, 0.01D, player.motionZ);
			}
		}
	}
}
