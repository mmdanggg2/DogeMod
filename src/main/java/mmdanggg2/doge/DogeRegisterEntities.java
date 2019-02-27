package mmdanggg2.doge;

import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.entities.DogeProjectile;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class DogeRegisterEntities {
	private static int entityID = -1;
	
	public static void register(IForgeRegistry<EntityEntry> entityReg) {
		// Entities
		entityReg.register(EntityEntryBuilder.create()
				.entity(DogeProjectile.class)
				.name("doge_projectile")
				.id(new ResourceLocation(DogeInfo.ID, "doge_projectile"), ++entityID)
				.tracker(128, 1, true)
				.build() );
		
		// Mobs
		EntityEntryBuilder<Entity> dMobBuilder = EntityEntryBuilder.create()
				.entity(DogeMob.class)
				.name("doge_mob")
				.id(new ResourceLocation(DogeInfo.ID, "doge_mob"), ++entityID)
				.tracker(128, 1, true)
				.egg(0xeaeae9, 0xc99a03);
		
		if (DogeInfo.shibeSpawnBiomes.length > 0) {
			int[] biomes = DogeInfo.shibeSpawnBiomes;
			for (int i = 0; i < biomes.length; i++) {
				DogeLogger.logDebug("Adding doge spawn biome " + biomes[i]);
				dMobBuilder.spawn(EnumCreatureType.CREATURE, DogeInfo.shibeSpawnChance, DogeInfo.shibeSpawnMinSize, DogeInfo.shibeSpawnMaxSize, Biome.getBiome(biomes[i]));
			}
		}
		entityReg.register(dMobBuilder.build());
		
	}
}
