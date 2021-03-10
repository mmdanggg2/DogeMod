package mmdanggg2.doge.entity;

import mmdanggg2.doge.init.DogeEntityTypes;
import mmdanggg2.doge.init.DogeItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DogeProjectile extends ProjectileItemEntity
{
	
	public boolean dropCoin = true;
	public float damage = 12.0f;
	
	public DogeProjectile(EntityType<? extends DogeProjectile> entType, World world)
	{
		super(entType, world);
	}
	
	public DogeProjectile(LivingEntity thrower, World world)
	{
		super(DogeEntityTypes.DOGECOIN.get(), thrower, world);
		this.setMotion(this.getMotion().mul(1.5, 1.5, 1.5));
	}
	
	public DogeProjectile(double x, double y, double z, World world)
	{
		super(DogeEntityTypes.DOGECOIN.get(), x, y, z, world);
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.04f;
	}
	
	@Override
	protected Item getDefaultItem() {
		return DogeItems.DOGECOIN.get();
	}
	
	@Override
	protected void onEntityHit(EntityRayTraceResult hitResult) {
		super.onEntityHit(hitResult);
		Entity hitEntity = hitResult.getEntity();
		//func_234616_v_ == getThrower
		if (hitEntity == func_234616_v_()) return;
		
		hitEntity.attackEntityFrom(DamageSource.causeThrownDamage(this, func_234616_v_()), this.damage);
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
	
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(RayTraceResult traceResult)
	{
		super.onImpact(traceResult);
		if (!world.isRemote) {
			if (dropCoin) {
				ItemEntity coin = new ItemEntity(world, getPosX(), getPosY(), getPosZ(), new ItemStack(DogeItems.DOGECOIN.get(), 1));
				world.addEntity(coin);
			}
			setDead();
		}
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}