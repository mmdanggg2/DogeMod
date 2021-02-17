package mmdanggg2.doge.entities;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.config.DogeConfig;
import mmdanggg2.doge.init.DogeItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class DogeProjectile extends ThrowableEntity
{
	
	public boolean dropCoin = true;
	public float damage;
	
	public DogeProjectile(World par1World)
	{
		super(par1World);
	}
	
	public DogeProjectile(World par1World, LivingEntity par2EntityLivingBase)
	{
		super(par1World, par2EntityLivingBase);
		this.motionX = this.motionX * 1.5;
		this.motionY = this.motionY * 1.5;
		this.motionZ = this.motionZ * 1.5;
		damage = DogeConfig.ServerConfig.toolDamage * 2.0F;
	}
	
	public DogeProjectile(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.04f;
	}
	
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(RayTraceResult traceResult)
	{
		if (traceResult.getType() == Type.ENTITY)
		{
			Entity hitEntity = ((EntityRayTraceResult)traceResult).getEntity();
			if (hitEntity == thrower) return;
			
			hitEntity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), this.damage);
			if (this.isBurning()) {
				hitEntity.setFire(6);
			}
			if (hitEntity instanceof LivingEntity) {
				LivingEntity hitEntLiving = (LivingEntity) hitEntity;
				if (hitEntLiving.getHealth() == 0)
				{
					dropCoin = false;
				}
			}
		}
		
		//        for (int j = 0; j < 8; ++j)
		//        {
		//            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		//        }
		
		if (!world.isRemote)
		{
			if (dropCoin)
			{
				ItemEntity coin = new ItemEntity(world, getPosX(), getPosY(), getPosZ(), new ItemStack(DogeItems.dogecoin, 1));
				world.addEntity(coin);
			}
			setDead();
		}
	}
}