package mmdanggg2.doge;

import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.entities.DogeProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.registry.EntityRegistry;

public class DogeRegisterEntities {
	public static void register() {
		// Entities
		EntityRegistry.registerModEntity(DogeProjectile.class, "dogeProjectile", EntityRegistry.findGlobalUniqueEntityId(), Doge.instance, 128, 1, true);
		
		// Mobs
		registerMobEntity(DogeMob.class, "dogeMob", 0xeaeae9, 0xc99a03);
	}
	
	/**
	 * Registers an entity as a mob and creates a spawn egg of that entity
	 * 
	 * @param entityClass
	 *            The Class to register
	 * @param entityName
	 *            The name of the what is being registered
	 * @param bkEggColor
	 *            Spawn Egg background colour
	 * @param fgEggColor
	 *            Spawn Egg foreground colour
	 */
	public static void registerMobEntity(Class<? extends Entity> entityClass,
			String entityName, int bkEggColor, int fgEggColor) {
		int id = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
		EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, bkEggColor, fgEggColor));
	}
	
	public static void addSpawn(Class<? extends EntityLiving> entityClass,
			int spawnProb, int min, int max, BiomeGenBase[] biomes) {
		if (spawnProb > 0) {
			EntityRegistry.addSpawn(entityClass, spawnProb, min, max, EnumCreatureType.creature, biomes);
		}
	}
}
