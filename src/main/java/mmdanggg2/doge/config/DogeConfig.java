package mmdanggg2.doge.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

@EventBusSubscriber(modid = Doge.ID, bus = EventBusSubscriber.Bus.MOD)
public class DogeConfig {
	public static final ServerConfig SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;
	static {
		final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
		SERVER_SPEC = specPair.getRight();
		SERVER = specPair.getLeft();
	}
	
	public static int rigChance;
	public static int rigSpeed;
	
	public static int gpuChance;
	public static float gpuSpeedStart;
	public static float gpuSpeedStep;
	public static int gpuCoolRate;
	
	public static List<? extends String> dogecoinConvertList;

	public static List<ResourceLocation> shibaSpawnBiomes;
	public static int shibaSpawnChance;
	public static int shibaSpawnMinSize;
	public static int shibaSpawnMaxSize;
	public static float shibaAtkDamage;

	public static boolean debug;
	
	@SubscribeEvent
	public static void onModConfigEvent(ModConfig.ModConfigEvent configEvent) {
		if (configEvent.getConfig().getSpec() == SERVER_SPEC) {
			bakeServerConfig();
		}
	}
	
	public static void bakeServerConfig() {
		rigChance = SERVER.rigChance.get();
		rigSpeed = SERVER.rigSpeed.get();
		
		gpuChance = SERVER.gpuChance.get();
		gpuSpeedStart = SERVER.gpuSpeedStart.get().floatValue();
		gpuSpeedStep = SERVER.gpuSpeedStep.get().floatValue();
		gpuCoolRate = SERVER.gpuCoolRate.get();
		
		dogecoinConvertList = SERVER.dogecoinConvertList.get();
		
		shibaSpawnBiomes = new ArrayList<>();
		List<? extends String> shibaSpawnBiomeStrings = SERVER.shibaSpawnBiomes.get();
		if (shibaSpawnBiomeStrings != null) {
			for (String biomeStr : shibaSpawnBiomeStrings) {
				try {
					ResourceLocation biomeRL = new ResourceLocation(biomeStr);
					shibaSpawnBiomes.add(biomeRL);
				} catch (ResourceLocationException e) {
					DogeLogger.logger.error("Invalid shibaSpawnBiome entry", e);
				}
			}
		}
		shibaSpawnChance = SERVER.shibaSpawnChance.get();
		shibaSpawnMinSize = SERVER.shibaSpawnMinSize.get();
		shibaSpawnMaxSize = SERVER.shibaSpawnMaxSize.get();
		shibaAtkDamage = SERVER.shibaAtkDamage.get().floatValue();
		
		debug = SERVER.debug.get();
	}

	public static class ServerConfig {
		public final IntValue rigChance;
		public final IntValue rigSpeed;
		
		public final IntValue gpuChance;
		public final DoubleValue gpuSpeedStart;
		public final DoubleValue gpuSpeedStep;
		public final IntValue gpuCoolRate;
		
		public final ConfigValue<List<? extends String>> dogecoinConvertList;

		public final ConfigValue<List<? extends String>> shibaSpawnBiomes;
		public final IntValue shibaSpawnChance;
		public final IntValue shibaSpawnMinSize;
		public final IntValue shibaSpawnMaxSize;
		public final DoubleValue shibaAtkDamage;

		public final BooleanValue debug;

		public ServerConfig(ForgeConfigSpec.Builder builder) {
			builder.push("mining_rig");
			rigChance = builder.comment("How likely a GPU is to get a Dogecoin, lower is more likely. There is a 1 in x chance").defineInRange("rigChance", 5, 1, Integer.MAX_VALUE);
			rigSpeed = builder.comment("How fast the rig uses the GPUs, lower is faster").defineInRange("rigSpeed", 100, 1, Integer.MAX_VALUE);
			builder.pop();

			builder.push("gpu");
			gpuChance = builder.comment("How likely a GPU is to get a Dogecoin, lower is more likely. There is a 1 in x chance per block.").defineInRange("gpuChance", 11, 1, Integer.MAX_VALUE);
			gpuSpeedStart = builder.comment("The mining speed of the GPU when fully cooled").defineInRange("gpuSpeedStart", 1.0F, 0.0F, Float.MAX_VALUE);
			gpuSpeedStep = builder.comment("How much speed the GPU gains per mine").defineInRange("gpuSpeedStep", 2.0F, 0.0F, Float.MAX_VALUE);
			gpuCoolRate = builder.comment("How quickly the GPU cools down when not in hand, lower is faster").defineInRange("gpuCoolRate", 30, 1, Integer.MAX_VALUE);
			builder.pop();
			
			builder.push("dogecoin");
			dogecoinConvertList = builder.comment("This is a list of Java classes that are able to be converted into Shiba's by a coin").defineList("dogecoinConvertList", Arrays.asList(
					"PigEntity",
					"WolfEntity",
					"ChickenEntity",
					"CowEntity",
					"OcelotEntity",
					"SheepEntity",
					"SquidEntity",
					"CreeperEntity",
					"SnowmanEntity"), x->true);
			builder.pop();
			
			builder.push("shiba");
			shibaSpawnBiomes = builder.comment("This is a list of biome id's that shibas can spawn in").defineList("shibaSpawnBiomes", Arrays.asList("minecraft:forest", "minecraft:taiga", "wooded_hills", "taiga_hills", "dark_forest"), x->true);
			shibaSpawnChance = builder.comment("How likely the shibas spawn in the biomes, lower is less likely").defineInRange("shibaSpawnChance", 4, 0, Integer.MAX_VALUE);
			shibaSpawnMinSize = builder.comment("Minimum amount of shibas that spawn when they do").defineInRange("shibaSpawnMinSize", 1, 0, Integer.MAX_VALUE);
			shibaSpawnMaxSize = builder.comment("Maximum amount of shibas that spawn when they do").defineInRange("shibaSpawnMaxSize", 6, 0, Integer.MAX_VALUE);
			shibaAtkDamage = builder.comment("Amount of damage a shiba will do when tame, halved when wild").defineInRange("shibaAtkDamage", 8, 0, Float.MAX_VALUE);
			builder.pop();
			
			builder.push("debug");
			debug = builder.comment("Show debug output in log").define("debug", false);
			builder.pop();
		}

	}
}
