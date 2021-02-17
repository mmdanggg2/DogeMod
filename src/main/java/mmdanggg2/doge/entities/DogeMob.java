package mmdanggg2.doge.entities;

import java.util.UUID;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.init.DogeItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DogeMob extends WolfEntity
{
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.<Boolean>createKey(DogeMob.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.<Integer>createKey(DogeMob.class, DataSerializers.VARINT);
	public DogeMob(EntityType<? extends WolfEntity> type, World world)
	{
		super(type, world);
	}
    
    @Override
    protected void registerData()
    {
        super.registerData();
        this.dataManager.set(BEGGING, Boolean.valueOf(false));
        this.dataManager.register(COLLAR_COLOR, Integer.valueOf(DyeColor.RED.getId()));
    }
	
	@Override
	protected AttributeModifierMap.MutableAttribute applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35D);
		
		if (this.isTamed())
		{
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(200.0D);
			this.setHealth(200.0f);
		}
		else
		{
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(50.0D);
		}
	}
	
	@Override
	public void setTamed(boolean par1)
	{
		super.setTamed(par1);
		
		if (par1)
		{
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(200.0D);
		}
		else
		{
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(50.0D);
		}
	}
	
	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 * 
	 */
	@Override
	public ActionResultType func_230254_b_(PlayerEntity player, Hand hand)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();
		
		if (!itemstack.isEmpty() && itemstack.getItem() == DogeItems.dogecoin && !this.func_233678_J__() && !this.isTamed())
		{
			if (!player.abilities.isCreativeMode)
			{
				itemstack.shrink(1);
			}
			
			if (itemstack.isEmpty())
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
			}
			
			if (this.isServerWorld())
			{
				makeTamed(player);
				this.aiSit.setSitting(true);
			}
			
			return ActionResultType.SUCCESS;
		}
		
		if (!itemstack.isEmpty() && itemstack.getItem() == Items.BONE && !this.func_233678_J__()) { return ActionResultType.FAIL; }
		
		return super.func_230254_b_(player, hand);
	}
	
	/*
	@Override
    protected SoundEvent getAmbientSound()
    {
        return this.isAngry() ? SoundEvents.ENTITY_WOLF_GROWL : (this.rand.nextInt(3) == 0 ? (this.isTamed() && ((Float)this.dataManager.get(DATA_HEALTH_ID)).floatValue() < 10.0F ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT) : SoundEvents.ENTITY_WOLF_AMBIENT);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getTailRotation()
	{
		return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.75F - (200.0F - ((Float)this.dataManager.get(DATA_HEALTH_ID)).floatValue()) * 0.003F) * (float)Math.PI : ((float)Math.PI / 5F));
	}*/
	
	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
	 * the animal type)
	 */
	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return par1ItemStack == null ? false : (!(par1ItemStack.getItem() instanceof Food) ? false : ((ItemFood) par1ItemStack.getItem()).isWolfsFavoriteMeat());
	}
	
	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected Item getDropItem()
	{
		return DogeItems.dogecoin;
	}
	
	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk()
	{
		return 32;
	}
	
	/**
	 * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
	 */
	@Override
	public DogeMob func_241840_a(ServerWorld sWorld, AgeableEntity par1EntityAgeable)
	{
		DogeMob childAnimal = new DogeMob((EntityType<DogeMob>)EntityType.byKey("dogeMob").get(), this.world);
		UUID owner = this.getOwnerId();
		
		if (owner != null) {
			childAnimal.setOwnerId(owner);
			childAnimal.setTamed(true);
		}
		
		return childAnimal;
	}
	
	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(AnimalEntity animal)
	{
		if (animal == this)
		{
			return false;
		}
		else if (!this.isTamed())
		{
			return false;
		}
		else if (!(animal instanceof DogeMob))
		{
			return false;
		}
		else
		{
			DogeMob doge = (DogeMob)animal;
            if (!doge.isTamed())
            {
                return false;
            }
            else if (doge.isSitting())
            {
                return false;
            }
            else
            {
                return this.isInLove() && doge.isInLove();
            }
		}
	}
	
	public void makeTamed(PlayerEntity player) {
		this.setTamed(true);
		this.navigator.clearPath();
		this.setAttackTarget((LivingEntity)null);
		//		this.aiSit.setSitting(true);
		this.setHealth(200.0F);
		this.setOwnerId(player.getUniqueID());
		this.playTameEffect(true);
		this.world.setEntityState(this, (byte)7);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		float atk = DogeInfo.shibeAtkDamage;
		if (this.isTamed()) {
			return entity.attackEntityFrom(DamageSource.causeMobDamage(this), atk);
		}
		else {
			return entity.attackEntityFrom(DamageSource.causeMobDamage(this), atk / 2);
		}
	}
}
