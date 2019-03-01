package mmdanggg2.doge.entities;

import java.util.UUID;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DogeMob extends EntityWolf
{
    private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.<Float>createKey(DogeMob.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.<Boolean>createKey(DogeMob.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.<Integer>createKey(DogeMob.class, DataSerializers.VARINT);
	public DogeMob(World par1World)
	{
		super(par1World);
		
	}
	
	@Override
    protected void updateAITasks()
    {
        this.dataManager.set(DATA_HEALTH_ID, Float.valueOf(this.getHealth()));
    }
    
    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(DATA_HEALTH_ID, Float.valueOf(this.getHealth()));
        this.dataManager.register(BEGGING, Boolean.valueOf(false));
        this.dataManager.register(COLLAR_COLOR, Integer.valueOf(EnumDyeColor.RED.getDyeDamage()));
    }
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
		
		if (this.isTamed())
		{
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
		}
	}
	
	@Override
	public void setTamed(boolean par1)
	{
		super.setTamed(par1);
		
		if (par1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
		}
	}
	
	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean processInteract(EntityPlayer par1EntityPlayer, EnumHand hand)
	{
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
		
		if (!itemstack.isEmpty() && itemstack.getItem() == Doge.dogecoin && !this.isAngry() && !this.isTamed())
		{
			if (!par1EntityPlayer.capabilities.isCreativeMode)
			{
				itemstack.shrink(1);
			}
			
			if (itemstack.isEmpty())
			{
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
			}
			
			if (this.isServerWorld())
			{
				makeTamed(par1EntityPlayer);
				this.aiSit.setSitting(true);
			}
			
			return true;
		}
		
		if (!itemstack.isEmpty() && itemstack.getItem() == Items.BONE && !this.isAngry()) { return false; }
		
		return super.processInteract(par1EntityPlayer, hand);
	}
	
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
	}
	
	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
	 * the animal type)
	 */
	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return par1ItemStack == null ? false : (!(par1ItemStack.getItem() instanceof ItemFood) ? false : ((ItemFood) par1ItemStack.getItem()).isWolfsFavoriteMeat());
	}
	
	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected Item getDropItem()
	{
		return Doge.dogecoin;
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
	public DogeMob createChild(EntityAgeable par1EntityAgeable)
	{
		DogeMob childAnimal = new DogeMob(this.world);
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
	public boolean canMateWith(EntityAnimal animal)
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
	
	public void makeTamed(EntityPlayer player) {
		this.setTamed(true);
		this.navigator.clearPath();
		this.setAttackTarget((EntityLivingBase)null);
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
