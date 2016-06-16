package mmdanggg2.doge;

import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.entities.DogeProjectile;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class DogeRegisterEntities {
	private static int entityID = -1;
	
	public static void register() {
		// Entities
		EntityRegistry.registerModEntity(DogeProjectile.class, "dogeProjectile", ++entityID, Doge.instance, 128, 1, true);
		
		// Mobs
		EntityRegistry.registerModEntity(DogeMob.class, "dogeMob", ++entityID, Doge.instance, 128, 1, true, 0xeaeae9, 0xc99a03);
		
		if (DogeInfo.shibeSpawnBiomes.length > 0) {
			int[] biomes = DogeInfo.shibeSpawnBiomes;
			Biome[] shibeBiomeList = new Biome[biomes.length];
			for (int i = 0; i < biomes.length; i++) {
				DogeLogger.logDebug("Adding biome " + biomes[i]);
				shibeBiomeList[i] = Biome.getBiome(biomes[i]);
			}
			addSpawn(DogeMob.class, DogeInfo.shibeSpawnChance, DogeInfo.shibeSpawnMinSize, DogeInfo.shibeSpawnMaxSize, shibeBiomeList);
		}
	}
	
	public static void addSpawn(Class<? extends EntityLiving> entityClass, int spawnProb, int min, int max, Biome[] biomes) {
		if (spawnProb > 0) {
			EntityRegistry.addSpawn(entityClass, spawnProb, min, max, EnumCreatureType.CREATURE, biomes);
		}
	}
}
