package mmdanggg2.doge.entities;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DogeMob extends EntityWolf
{
	public DogeMob(World par1World)
	{
		super(par1World);
		
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
		
		if (this.isTamed())
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
		}
	}
	
	@Override
	public void setTamed(boolean par1)
	{
		super.setTamed(par1);
		
		if (par1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
		}
	}
	
	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
		
		if (itemstack != null && itemstack.getItem() == Doge.dogecoin && !this.isAngry() && !this.isTamed())
		{
			if (!par1EntityPlayer.capabilities.isCreativeMode)
			{
				--itemstack.stackSize;
			}
			
			if (itemstack.stackSize <= 0)
			{
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
			}
			
			if (!this.worldObj.isRemote)
			{
				makeTamed(par1EntityPlayer);
				this.aiSit.setSitting(true);
			}
			
			return true;
		}
		
		if (itemstack != null && itemstack.getItem() == Items.bone && !this.isAngry()) { return false; }
		
		return super.interact(par1EntityPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getTailRotation()
	{
		return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.75F - (200.0F - this.dataWatcher.getWatchableObjectFloat(18)) * 0.003F) * (float)Math.PI : ((float)Math.PI / 5F));
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
		DogeMob childAnimal = new DogeMob(this.worldObj);
		String owner = this.func_152113_b();
		
		if (owner != null && owner.trim().length() > 0) {
			childAnimal.func_152115_b(owner);
			childAnimal.setTamed(true);
		}
		
		return childAnimal;
	}
	
	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(EntityAnimal par1EntityAnimal)
	{
		if (par1EntityAnimal == this)
		{
			return false;
		}
		else if (!this.isTamed())
		{
			return false;
		}
		else if (!(par1EntityAnimal instanceof DogeMob))
		{
			return false;
		}
		else
		{
			DogeMob entitywolf = (DogeMob)par1EntityAnimal;
			return !entitywolf.isTamed() ? false : (entitywolf.isSitting() ? false : this.isInLove() && entitywolf.isInLove());
		}
	}
	
	public void makeTamed(EntityPlayer par1EntityPlayer) {
		this.setTamed(true);
		this.setPathToEntity((PathEntity)null);
		this.setAttackTarget((EntityLivingBase)null);
		//		this.aiSit.setSitting(true);
		this.setHealth(200.0F);
		this.func_152115_b(par1EntityPlayer.getUniqueID().toString());
		this.playTameEffect(true);
		this.worldObj.setEntityState(this, (byte)7);
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
