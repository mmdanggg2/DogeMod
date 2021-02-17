package mmdanggg2.doge;

import mmdanggg2.doge.config.DogeConfig;
import mmdanggg2.doge.init.DogeBlocks;
import mmdanggg2.doge.item.DogeArmour;
import mmdanggg2.doge.item.DogeGroup;
import mmdanggg2.doge.item.DogeLauncher;
import mmdanggg2.doge.item.Dogecoin;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = Doge.ID, bus = EventBusSubscriber.Bus.MOD)
public class DogeRegister {

	// Items
	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		DogeLogger.logInfo("Registering Items");
		
		IForgeRegistry<Item> itemReg = event.getRegistry();
		
		// Items
		itemReg.register(new Dogecoin());
		
		itemReg.register(new DogeLauncher());
		
		//itemReg.register(new GPU());
		
		// Doge Tools
		itemReg.register(new PickaxeItem(ItemTier.DIAMOND,
				DogeConfig.toolDamage,
				DogeConfig.toolSpeed,
				new Item.Properties()
					.group(DogeGroup.dogeGroup))
				.setRegistryName("doge_pickaxe"));
		
		itemReg.register(new AxeItem(ItemTier.DIAMOND,
				DogeConfig.toolDamage,
				DogeConfig.toolSpeed,
				new Item.Properties()
					.group(DogeGroup.dogeGroup))
				.setRegistryName("doge_axe"));
		
		itemReg.register(new ShovelItem(ItemTier.DIAMOND,
				DogeConfig.toolDamage,
				DogeConfig.toolSpeed,
				new Item.Properties()
					.group(DogeGroup.dogeGroup))
				.setRegistryName("doge_shovel"));
		
		itemReg.register(new HoeItem(ItemTier.DIAMOND,
				DogeConfig.toolDamage,
				DogeConfig.toolSpeed,
				new Item.Properties()
					.group(DogeGroup.dogeGroup))
				.setRegistryName("doge_hoe"));
		
		itemReg.register(new SwordItem(ItemTier.DIAMOND,
				DogeConfig.toolDamage,
				DogeConfig.toolSpeed,
				new Item.Properties()
					.group(DogeGroup.dogeGroup))
				.setRegistryName("doge_sword"));
		
		// Doge Armour
		itemReg.register(new DogeArmour(EquipmentSlotType.HEAD, "doge_helmet"));
		
		itemReg.register(new DogeArmour(EquipmentSlotType.CHEST, "doge_chestplate"));
		
		itemReg.register(new DogeArmour(EquipmentSlotType.LEGS, "doge_leggings"));
		
		itemReg.register(new DogeArmour(EquipmentSlotType.FEET, "doge_boots"));
		
		// TODO Block Items
		itemReg.register(new BlockItem(DogeBlocks.doge_block, new Item.Properties().group(DogeGroup.dogeGroup)).setRegistryName(DogeBlocks.doge_block.getRegistryName()));
		//itemReg.register(new BlockItem(DogeBlocks.mining_rig, new Item.Properties().group(DogeGroup.dogeGroup)).setRegistryName(DogeBlocks.mining_rig.getRegistryName()));
	}
	
	// Blocks
	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		DogeLogger.logInfo("Registering Blocks");
		
		IForgeRegistry<Block> blockReg = event.getRegistry();
		
		blockReg.register(new Block(Block.Properties.create(Material.IRON, MaterialColor.GOLD).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)).setRegistryName("doge_block"));
		
		//blockReg.register(new MiningRig());
	}
	
	/*/ Tile entities
	@SubscribeEvent
	public static void onRegisterTileEntities(RegistryEvent.Register<TileEntityType> event) {
		DogeLogger.logInfo("Registering Tile entities");

		IForgeRegistry<TileEntityType> tileEntityReg = event.getRegistry();
		
		tileEntityReg.register(MiningRigTileEntity.class, new ResourceLocation(Doge.ID, "mining_rig_tile_entity"));
	}
	
	// Entities
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
