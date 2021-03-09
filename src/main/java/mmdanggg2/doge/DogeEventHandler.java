package mmdanggg2.doge;

import java.util.List;

import mmdanggg2.doge.config.DogeConfig;
import mmdanggg2.doge.init.DogeEntityTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Doge.ID, bus = EventBusSubscriber.Bus.FORGE)
public class DogeEventHandler {
	
	@SubscribeEvent
	public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
		if (DogeConfig.shibaSpawnBiomes != null) {
			for (ResourceLocation biomeRL : DogeConfig.shibaSpawnBiomes) {
				if (event.getName().equals(biomeRL)) {
					List<Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
					spawns.add(new MobSpawnInfo.Spawners(DogeEntityTypes.SHIBA.get(), DogeConfig.shibaSpawnChance, DogeConfig.shibaSpawnMinSize, DogeConfig.shibaSpawnMaxSize));
				}
			}
		}
	}
}
