package mmdanggg2.doge.entity;

import java.util.UUID;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.config.DogeConfig;
import mmdanggg2.doge.entity.ai.DogeBegGoal;
import mmdanggg2.doge.init.DogeEntityTypes;
import mmdanggg2.doge.init.DogeItems;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BegGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

public class ShibaEntity extends WolfEntity
{
	public ShibaEntity(EntityType<? extends ShibaEntity> type, World world)
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
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new SitGoal(this));
		//this.goalSelector.addGoal(3, new WolfEntity.AvoidEntityGoal(this, LlamaEntity.class, 24.0F, 1.5D, 1.5D));
		this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
		this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new DogeBegGoal(this, 8.0F));
		this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
		this.targetSelector.addGoal(5, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, TARGET_ENTITIES));
		this.targetSelector.addGoal(6, new NonTamedTargetGoal<>(this, TurtleEntity.class, false, TurtleEntity.TARGET_DRY_BABY));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));
		this.targetSelector.addGoal(8, new ResetAngerGoal<>(this, true));
	}
	
	@Override
	public void setTamed(boolean tamed)
	{
		super.setTamed(tamed);
		if (tamed) {
			getAttribute(Attributes.MAX_HEALTH).setBaseValue(200.0D);
			getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(DogeConfig.shibaAtkDamage);
			this.setHealth(200.0F);
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
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public float getTailRotation() {
		if (isTamed()) {
			return (getHealth() / getMaxHealth()) * 2.5f;
		} else {
			return super.getTailRotation();
		}
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
