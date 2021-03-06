package mmdanggg2.doge.items;

import java.util.Random;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class Dogecoin extends Item {
	public Dogecoin() {
		super();
		setMaxStackSize(64);
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogecoin");
		setRegistryName("dogecoin");
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer player, EntityLivingBase activatedEntity, EnumHand hand) {
		if (activatedEntity != null) {
			String entClassStr = activatedEntity.getClass().getSimpleName();
			DogeLogger.logDebug("Coin clicked on class: " + entClassStr);
			Boolean match = false;
			for (String str : DogeInfo.dogecoinConvertList) {
				//DogeLogger.logDebug("Checking: " + str + " == " + entClassStr);
				if (entClassStr.equals(str)) {
					//DogeLogger.logDebug("Class match! " + str);
					if (activatedEntity instanceof EntityTameable) {
						DogeLogger.logDebug("Entity is tameable");
						if (!(((EntityTameable) activatedEntity).getOwner() == null || ((EntityTameable) activatedEntity).getOwner() == player)) {
							DogeLogger.logDebug("it is owned");
							match = false;
							break;
						}
					}
					match = true;
					break;
				}
			}
			if (match && !(activatedEntity instanceof EntityPlayer)) {
				DogeLogger.logDebug("WE HAVE A MATCH!!");
				if (!player.capabilities.isCreativeMode)
				{
					par1ItemStack.shrink(1);
				}
				if (activatedEntity.isServerWorld()) {
					World spawnWorld = activatedEntity.getEntityWorld();
					DogeMob newDoge = new DogeMob(spawnWorld);
					newDoge.setLocationAndAngles(
							activatedEntity.posX,
							activatedEntity.posY,
							activatedEntity.posZ,
							activatedEntity.rotationYaw,
							activatedEntity.rotationPitch);
					activatedEntity.setDead();
					spawnWorld.spawnEntity(newDoge);
					newDoge.makeTamed(player);
				}
				return true;
			}
			else {
				Random rand = player.getRNG();
				for (int i = 0; i < 7; ++i)
		        {
		            double d0 = rand.nextGaussian() * 0.02D;
		            double d1 = rand.nextGaussian() * 0.02D;
		            double d2 = rand.nextGaussian() * 0.02D;
		            player.getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
		            		activatedEntity.posX + (rand.nextFloat() * activatedEntity.width * 2.0F) - activatedEntity.width,
		            		activatedEntity.posY + 0.5D + (rand.nextFloat() * activatedEntity.height),
		            		activatedEntity.posZ + (rand.nextFloat() * activatedEntity.width * 2.0F) - activatedEntity.width,
		            		d0, d1, d2);
		        }
			}
		}
		return false;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return EnumActionResult.PASS;
	}
}
