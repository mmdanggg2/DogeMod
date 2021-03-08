package mmdanggg2.doge.init;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.entity.DogeProjectile;
import mmdanggg2.doge.entity.ShibaEntity;
import mmdanggg2.doge.item.Dogecoin;
import mmdanggg2.doge.tileentitiy.MiningRigTileEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DogeEntityTypes {
	
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Doge.ID);
	
	private static final String DOGECOIN_NAME = "dogecoin";
	public static final RegistryObject<EntityType<DogeProjectile>> DOGECOIN = ENTITY_TYPES.register(DOGECOIN_NAME, ()-> 
		EntityType.Builder.<DogeProjectile>create(DogeProjectile::new, EntityClassification.MISC).size(0.25f, 0.25f).build(DOGECOIN_NAME));

	private static final String SHIBA_NAME = "shiba";
	protected static final EntityType<ShibaEntity> SHIBA_PRE_INIT_TYPE = EntityType.Builder.<ShibaEntity>create(ShibaEntity::new, EntityClassification.CREATURE).size(EntityType.WOLF.getWidth(), EntityType.WOLF.getHeight()).build(SHIBA_NAME);
	public static final RegistryObject<EntityType<ShibaEntity>> SHIBA = ENTITY_TYPES.register(SHIBA_NAME, ()-> SHIBA_PRE_INIT_TYPE);

}
