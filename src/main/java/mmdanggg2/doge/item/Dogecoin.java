package mmdanggg2.doge.item;

import java.util.Random;

import mmdanggg2.doge.config.DogeConfig;
import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Dogecoin extends Item {
	public Dogecoin() {
		super(new Item.Properties().maxStackSize(64).group(DogeGroup.dogeGroup));
		setRegistryName("dogecoin");
	}
	
	@Override
	public ActionResultType itemInteractionForEntity(ItemStack par1ItemStack, PlayerEntity player, LivingEntity activatedEntity, Hand hand) {
		if (activatedEntity != null) {
			String entClassStr = activatedEntity.getClass().getSimpleName();
			DogeLogger.logDebug("Coin clicked on class: " + entClassStr);
			Boolean match = false;
			for (String str : DogeConfig.ServerConfig.dogecoinConvertList) {
				//DogeLogger.logDebug("Checking: " + str + " == " + entClassStr);
				if (entClassStr.equals(str)) {
					//DogeLogger.logDebug("Class match! " + str);
					if (activatedEntity instanceof TameableEntity) {
						DogeLogger.logDebug("Entity is tameable");
						if (!(((TameableEntity) activatedEntity).getOwner() == null || ((TameableEntity) activatedEntity).getOwner() == player)) {
							DogeLogger.logDebug("it is owned");
							match = false;
							break;
						}
					}
					match = true;
					break;
				}
			}
			if (match && !(activatedEntity instanceof PlayerEntity)) {
				DogeLogger.logDebug("WE HAVE A MATCH!!");
				if (!player.abilities.isCreativeMode)
				{
					par1ItemStack.shrink(1);
				}
				if (activatedEntity.isServerWorld()) {
					World spawnWorld = activatedEntity.getEntityWorld();
					DogeMob newDoge = new DogeMob(spawnWorld);
					newDoge.setLocationAndAngles(
							activatedEntity.getPosX(),
							activatedEntity.getPosY(),
							activatedEntity.getPosZ(),
							activatedEntity.rotationYaw,
							activatedEntity.rotationPitch);
					activatedEntity.remove();
					spawnWorld.addEntity(newDoge);
					newDoge.makeTamed(player);
				}
				return ActionResultType.SUCCESS;
			}
			else {
				Random rand = player.getRNG();
				for (int i = 0; i < 7; ++i)
		        {
		            double d0 = rand.nextGaussian() * 0.02D;
		            double d1 = rand.nextGaussian() * 0.02D;
		            double d2 = rand.nextGaussian() * 0.02D;
		            player.getEntityWorld().addParticle(ParticleTypes.SMOKE,
		            		activatedEntity.getPosX() + (rand.nextFloat() * activatedEntity.getWidth() * 2.0F) - activatedEntity.getWidth(),
		            		activatedEntity.getPosY() + 0.5D + (rand.nextFloat() * activatedEntity.getHeight()),
		            		activatedEntity.getPosZ() + (rand.nextFloat() * activatedEntity.getWidth() * 2.0F) - activatedEntity.getWidth(),
		            		d0, d1, d2);
		        }
			}
		}
		return ActionResultType.PASS;
	}
}
