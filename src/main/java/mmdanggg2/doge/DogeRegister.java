package mmdanggg2.doge;

import mmdanggg2.doge.client.gui.MiningRigScreen;
import mmdanggg2.doge.config.DogeConfig;
import mmdanggg2.doge.init.DogeBlocks;
import mmdanggg2.doge.init.DogeContainerTypes;
import mmdanggg2.doge.item.DogeArmour;
import mmdanggg2.doge.item.DogeGroup;
import mmdanggg2.doge.item.DogeLauncher;
import mmdanggg2.doge.item.Dogecoin;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = Doge.ID, bus = EventBusSubscriber.Bus.MOD)
public class DogeRegister {
	
	@SubscribeEvent
	public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {

		// Register Entity Renderers
		//RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WILD_BOAR.get(), WildBoarRenderer::new);
		//LOGGER.debug("Registered Entity Renderers");

		// Register ContainerType Screens
		// ScreenManager.registerFactory is not safe to call during parallel mod loading so we queue it to run later
		event.enqueueWork(() -> {
			ScreenManager.registerFactory(DogeContainerTypes.MINING_RIG.get(), MiningRigScreen::new);
			DogeLogger.logDebug("Registered ContainerType Screens");
		});

	}
	/*/ Entities
	@SubscribeEvent
	public static void onRegisterEntities(RegistryEvent.Register<EntityType> event) {
		DogeLogger.logInfo("Registering Entities");
		
		IForgeRegistry<EntityType> entityRegistry = event.getRegistry();
		
		entityReg.register(EntityEntryBuilder.create()
				.entity(DogeProjectile.class)
				.name("doge_projectile")
				.id(new ResourceLocation(Doge.ID, "doge_projectile"), ++entityID)
				.tracker(128, 1, true)
				.build() );
		
		// Mobs
		EntityType.Builder<DogeMob> dMobBuilder = EntityType.Builder.create(EntityClassification.CREATURE)
				.entity(DogeMob.class)
				.name("doge_mob")
				.id(new ResourceLocation(Doge.ID, "doge_mob"), ++entityID)
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
	
	/*TODO Recipies
	public static void onRegisterRecipies() {
		//Item Repair
		Doge.dogeToolMat.setRepairItem(new ItemStack(Doge.dogecoin));
		Doge.dogeArmorMat.setRepairItem(new ItemStack(Doge.dogecoin));
	}*/
}
