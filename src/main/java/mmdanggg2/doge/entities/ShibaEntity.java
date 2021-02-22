package mmdanggg2.doge.entities;

import java.util.UUID;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.config.DogeConfig;
import mmdanggg2.doge.init.DogeEntityTypes;
import mmdanggg2.doge.init.DogeItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

public class ShibaEntity extends WolfEntity
{
	public ShibaEntity(EntityType<? extends WolfEntity> type, World world)
	{
		super(type, world);
		setTamed(false);
	}
	
	public static AttributeModifierMap.MutableAttribute getCustomAttributes()
	{
		return MobEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D)
				.createMutableAttribute(Attributes.MAX_HEALTH, 50.0D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, DogeConfig.shibaAtkDamage);
	}
	
	@Override
	public void setTamed(boolean tamed)
	{
		super.setTamed(tamed);
		if (tamed) {
			getAttribute(Attributes.MAX_HEALTH).setBaseValue(200.0D);
			getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(DogeConfig.shibaAtkDamage);
		} else {
			getAttribute(Attributes.MAX_HEALTH).setBaseValue(50.0D);
			getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(DogeConfig.shibaAtkDamage/2);
		}
	}
	
	public void makeTamed(PlayerEntity player) {
		if (!world.isRemote) {
			super.setTamedBy(player);
			this.navigator.clearPath();
			this.setAttackTarget(null);
			this.setHealth(200.0F);
			this.world.setEntityState(this, (byte)7);
		}
	}
	
	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 * 
	 */
	@Override
	public ActionResultType func_230254_b_(PlayerEntity player, Hand hand)
	{
		ItemStack itemstack = player.getHeldItem(hand);
		Item item = itemstack.getItem();
		
		if (item == DogeItems.DOGECOIN.get() && !isTamed() && !func_233678_J__()) {
			if (world.isRemote) {
				return ActionResultType.CONSUME;
			} else {
				if (!player.abilities.isCreativeMode) {
					itemstack.shrink(1);
				}

				if (!ForgeEventFactory.onAnimalTame(this, player)) {
					makeTamed(player);
					this.func_233687_w_(true);//set sitting
				}
				
				return ActionResultType.SUCCESS;
			}
		}
		
		if (itemstack.getItem() == Items.BONE) {
			return ActionResultType.PASS;
		}
		
		return super.func_230254_b_(player, hand);
	}
	
	/**
	 * Returns the item ID for the item the mob drops on death.
	 *
	@Override
	protected Item getDropItem()
	{
		return DogeItems.DOGECOIN.get();
	}*/
	
	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk()
	{
		return 32;
	}
	
	/**
	 * createChild
	 * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
	 */
	@Override
	public ShibaEntity func_241840_a(ServerWorld sWorld, AgeableEntity parent)
	{
		ShibaEntity childAnimal = DogeEntityTypes.SHIBA.get().create(sWorld);
		UUID owner = this.getOwnerId();
		
		if (owner != null) {
			childAnimal.setOwnerId(owner);
			childAnimal.setTamed(true);
		}
		
		return childAnimal;
	}
	
	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 *
	@Override
	public boolean canMateWith(AnimalEntity animal)
	{
		if (animal == this) {
			return false;
		} else if (!this.isTamed()) {
			return false;
		} else if (!(animal instanceof DogeMob)) {
			return false;
		} else {
			DogeMob doge = (DogeMob) animal;
			if (!doge.isTamed()) {
				return false;
			} else if (doge.isSitting()) {
				return false;
			} else {
				return this.isInLove() && doge.isInLove();
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		float atk = DogeConfig.shibaAtkDamage;
		if (this.isTamed()) {
			return entity.attackEntityFrom(DamageSource.causeMobDamage(this), atk);
		}
		else {
			return entity.attackEntityFrom(DamageSource.causeMobDamage(this), atk / 2);
		}
	}*/
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
