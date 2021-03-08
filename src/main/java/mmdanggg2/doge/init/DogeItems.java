package mmdanggg2.doge.init;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.config.DogeConfig;
import mmdanggg2.doge.item.DogeArmour;
import mmdanggg2.doge.item.DogeGroup;
import mmdanggg2.doge.item.DogeLauncher;
import mmdanggg2.doge.item.Dogecoin;
import mmdanggg2.doge.item.GPU;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class DogeItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Doge.ID);
	
	// Tools
	public static final RegistryObject<PickaxeItem> DOGE_PICKAXE = ITEMS.register("doge_pickaxe", ()-> new PickaxeItem(ItemTier.DIAMOND, DogeConfig.toolDamage, DogeConfig.toolSpeed, new Item.Properties().group(DogeGroup.dogeGroup)));
	public static final RegistryObject<AxeItem> DOGE_AXE = ITEMS.register("doge_axe", ()-> new AxeItem(ItemTier.DIAMOND, DogeConfig.toolDamage, DogeConfig.toolSpeed, new Item.Properties().group(DogeGroup.dogeGroup)));
	public static final RegistryObject<ShovelItem> DOGE_SHOVEL = ITEMS.register("doge_shovel", ()-> new ShovelItem(ItemTier.DIAMOND, DogeConfig.toolDamage, DogeConfig.toolSpeed, new Item.Properties().group(DogeGroup.dogeGroup)));
	public static final RegistryObject<HoeItem> DOGE_HOE = ITEMS.register("doge_hoe", ()-> new HoeItem(ItemTier.DIAMOND, DogeConfig.toolDamage, DogeConfig.toolSpeed, new Item.Properties().group(DogeGroup.dogeGroup)));
	public static final RegistryObject<SwordItem> DOGE_SWORD = ITEMS.register("doge_sword", ()-> new SwordItem(ItemTier.DIAMOND, DogeConfig.toolDamage, DogeConfig.toolSpeed, new Item.Properties().group(DogeGroup.dogeGroup)));
	
	// Doge Armour
	public static final RegistryObject<DogeArmour> DOGE_HELMET = ITEMS.register("doge_helmet", ()-> new DogeArmour(EquipmentSlotType.HEAD));
	public static final RegistryObject<DogeArmour> DOGE_CHESTPLATE = ITEMS.register("doge_chestplate", ()-> new DogeArmour(EquipmentSlotType.CHEST));
	public static final RegistryObject<DogeArmour> DOGE_LEGGINGS = ITEMS.register("doge_leggings", ()-> new DogeArmour(EquipmentSlotType.LEGS));
	public static final RegistryObject<DogeArmour> DOGE_BOOTS = ITEMS.register("doge_boots", ()-> new DogeArmour(EquipmentSlotType.FEET));
	
	// Other
	public static final RegistryObject<Dogecoin> DOGECOIN = ITEMS.register("dogecoin", ()-> new Dogecoin());
	
	public static final RegistryObject<DogeLauncher> DOGE_LAUNCHER = ITEMS.register("doge_launcher", ()-> new DogeLauncher());
	
	public static final RegistryObject<GPU> GPU = ITEMS.register("gpu", ()-> new GPU());
	
	public static final RegistryObject<SpawnEggItem> SHIBA_EGG = ITEMS.register("shiba_egg", ()-> new SpawnEggItem(DogeEntityTypes.SHIBA_PRE_INIT_TYPE, 0xeaeae9, 0xc99a03, new Item.Properties().group(DogeGroup.dogeGroup)));
	
	//Blocks
	public static final RegistryObject<BlockItem> DOGE_BLOCK = ITEMS.register(DogeBlocks.DOGE_BLOCK.getId().getPath(), ()-> new BlockItem(DogeBlocks.DOGE_BLOCK.get(), new Item.Properties().group(DogeGroup.dogeGroup)));
	
	public static final RegistryObject<BlockItem> MINING_RIG = ITEMS.register(DogeBlocks.MINING_RIG.getId().getPath(), ()-> new BlockItem(DogeBlocks.MINING_RIG.get(), new Item.Properties().group(DogeGroup.dogeGroup)));
}
