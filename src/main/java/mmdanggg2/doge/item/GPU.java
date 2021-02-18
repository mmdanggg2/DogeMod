package mmdanggg2.doge.item;

import javax.annotation.Nullable;

import mmdanggg2.doge.config.DogeConfig;
import mmdanggg2.doge.init.DogeItems;
import mmdanggg2.doge.util.DogeLogger;
import mmdanggg2.doge.util.NBTHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class GPU extends Item {
	
	public GPU() {
		super(new Item.Properties().group(DogeGroup.dogeGroup).maxStackSize(1).maxDamage(20));
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, PlayerEntity player) {
		if (!world.isRemote) {
			if (!stack.hasTag()) {
				initTags(stack);
			}
			stack.setDamage(0);
		}
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return NBTHelper.getFloat(stack.getTag(), "speed", DogeConfig.gpuSpeedStart);
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, ToolType toolClass, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
		return 3;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, BlockState block, BlockPos pos, LivingEntity entityLiving) {
		if (!world.isRemote) {
			int xPos = pos.getX();
			int yPos = pos.getY();
			int zPos = pos.getZ();
			
			boolean mined = attemptMine(stack, world, DogeConfig.gpuChance);
			
			if (mined) {
				ItemEntity coin = new ItemEntity(world, xPos+.5, yPos+.5, zPos+.5, new ItemStack(DogeItems.DOGECOIN.get(), 1));
				world.addEntity(coin);
			}

			if (stack.getDamage() > stack.getMaxDamage()) {
				world.createExplosion(entityLiving, xPos, yPos, zPos, 2f, true, Explosion.Mode.BREAK);
				stack.setCount(0);
			}
		}
		return super.onBlockDestroyed(stack, world, block, pos, entityLiving);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean selected) {
		
		if (world.rand.nextInt(10) == 0 && selected) {
			randomDisplayTick(stack, world, entity);
		}

		if (!world.isRemote) {
			if (!stack.hasTag()) {
				initTags(stack);
			}
			
			CompoundNBT stackTag = stack.getTag();
			
			if (!selected) {
				int tickCount = NBTHelper.getInt(stackTag, "tickCount", 0);

				if (tickCount >= DogeConfig.gpuCoolRate) {
					tickCount = 0;
					if (stack.getDamage() > 0) {
						float speed = NBTHelper.getFloat(stackTag, "speed", DogeConfig.gpuSpeedStart);
						if (speed > DogeConfig.gpuSpeedStart) {
							speed = speed - DogeConfig.gpuSpeedStep;
							DogeLogger.logDebug("GPU Speed = " + speed);
							stackTag.putFloat("speed", speed);
						}
						stack.attemptDamageItem(-1, world.rand, null);
					}
				}
				tickCount++;
				stackTag.putInt("tickCount", tickCount);
			}
		}
		super.inventoryTick(stack, world, entity, itemSlot, selected);
	}

	@Override
	public boolean canHarvestBlock(BlockState blockState) {
		return true;
	}
	
	private void initTags(ItemStack stack) {
		CompoundNBT stackTag = new CompoundNBT();
		stackTag.putDouble("speed", DogeConfig.gpuSpeedStart);
		stackTag.putInt("tickCount", 0);
		stack.setTag(stackTag);
	}
	
	public boolean attemptMine(ItemStack stack, World world, int chance) {
		boolean mined = false;
		
		if (!stack.hasTag()) {
			initTags(stack);
		}
		
		stack.attemptDamageItem(1, world.rand, null);

		if (world.rand.nextInt(chance) == 0) {
			mined = true;
		}
		
		CompoundNBT stackTag = stack.getTag();
		float speed = NBTHelper.getFloat(stackTag, "speed", DogeConfig.gpuSpeedStart);
		speed = speed + DogeConfig.gpuSpeedStep;
		DogeLogger.logDebug("GPU Speed = " + speed);
		stackTag.putDouble("speed", speed);

		return mined;
	}
	
	private void randomDisplayTick(ItemStack stack, World world, Entity entity) {
		if (entity instanceof PlayerEntity && world.isRemote) {
			PlayerEntity player = (PlayerEntity) entity;
			double x = player.getPosX();
			double y = player.getPosY() + 1.5D;
			double z = player.getPosZ();
			float yaw = player.rotationYawHead;
			float pitch = player.rotationPitch;
			float depth;
			double offsetX;
			double offsetZ;
			double offsetY;
			
			if (Minecraft.getInstance().player == player && Minecraft.getInstance().gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
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
			Vector3d pMotion = player.getMotion();
			if (stack.getDamage() > 5) {
				world.addParticle(RedstoneParticleData.REDSTONE_DUST, x + offsetX, y + offsetY, z + offsetZ, 0, 0, 0);
			}
			if (stack.getDamage() > 10) {
				world.addParticle(RedstoneParticleData.REDSTONE_DUST, x + offsetX, y + offsetY, z + offsetZ, 0, 0, 0);
			}
			if (stack.getDamage() > 15) {
				world.addParticle(ParticleTypes.SMOKE, x + offsetX, y + offsetY, z + offsetZ, pMotion.x, 0.0D, pMotion.z);
			}
			if (stack.getDamage() > 18) {
				world.addParticle(ParticleTypes.FLAME, x + offsetX, y + offsetY, z + offsetZ, pMotion.x, 0.01D, pMotion.z);
			}
		}
	}
}
