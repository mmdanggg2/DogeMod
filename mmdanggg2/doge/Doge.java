package mmdanggg2.doge;

import mmdanggg2.doge.blocks.DogeBlock;
import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.entities.DogeProjectile;
import mmdanggg2.doge.items.DogeAxe;
import mmdanggg2.doge.items.DogeBoots;
import mmdanggg2.doge.items.DogeChestplate;
import mmdanggg2.doge.items.DogeHelmet;
import mmdanggg2.doge.items.DogeHoe;
import mmdanggg2.doge.items.DogeLauncher;
import mmdanggg2.doge.items.DogeLeggings;
import mmdanggg2.doge.items.DogePickaxe;
import mmdanggg2.doge.items.DogeShovel;
import mmdanggg2.doge.items.DogeSword;
import mmdanggg2.doge.items.Dogecoin;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid=BasicInfo.ID, name=BasicInfo.NAME, version=BasicInfo.VER)
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Doge {
	
	//Inits
	
	private static int dogecoinID;
	private static int dogeLauncherID;
	
	private static int dogePickaxeID;
	private static int dogeAxeID;
	private static int dogeShovelID;
	private static int dogeHoeID;
	private static int dogeSwordID;
	
	private static int dogeHelmetID;
	private static int dogeChestplateID;
	private static int dogeLeggingsID;
	private static int dogeBootsID;
	
	private static int dogeBlockID;
	
	public static EnumToolMaterial dogeToolMat = EnumHelper.addToolMaterial("Doge", 3, 600, 20.0F, 4.0F, 30);
	public static EnumArmorMaterial dogeArmorMat = EnumHelper.addArmorMaterial("Doge", 30, new int[]{5, 10, 8, 5}, 30);
	
	private static int dogeArmourRenderID;
	
	//Doge Tools
	public static Item dogePickaxe;
	public static Item dogeAxe;
	public static Item dogeShovel;
	public static Item dogeHoe;
	public static Item dogeSword;
	
	//Doge Armour
	public static Item dogeHelmet;
	public static Item dogeChestplate;
	public static Item dogeLeggings;
	public static Item dogeBoots;
	
	
	//Other
	public static Block dogeBlock;
	
	public static Item dogecoin;
	
	public static Item dogeLauncher;
	
	
	// The instance of your mod that Forge uses.
	@Instance(BasicInfo.NAME)
	public static Doge instance;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide=BasicInfo.CLIENTPROXY + "ClientProxy", serverSide=BasicInfo.COMMONPROXY + "CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		// loading the configuration from its file
		config.load();
		
		//Items
		dogecoinID = config.getItem("dogecoin", 5000).getInt();
		dogeLauncherID = config.getItem("dogeLauncher", 5010).getInt();
		
		//Doge Tools
		dogePickaxeID = config.getItem("dogePickaxe", 5001).getInt();
		dogeAxeID = config.getItem("dogeAxe", 5002).getInt();
		dogeShovelID = config.getItem("dogeShovel", 5003).getInt();
		dogeHoeID = config.getItem("dogeHoe", 5004).getInt();
		dogeSwordID = config.getItem("dogeSword", 5005).getInt();
		
		//Doge Armour
		dogeHelmetID = config.getItem("dogeHelmet", 5006).getInt();
		dogeChestplateID = config.getItem("dogeChestplate", 5007).getInt();
		dogeLeggingsID = config.getItem("dogeLeggings", 5008).getInt();
		dogeBootsID = config.getItem("dogeBoots", 5009).getInt();
		
		//Blocks
		dogeBlockID = config.getBlock("dogeBlock", 500).getInt();
		
		// saving the configuration to its file
		config.save();
		
		dogeToolMat.customCraftingMaterial = dogecoin;
		dogeArmorMat.customCraftingMaterial = dogecoin;
		
		//Items
		dogecoin = new Dogecoin(dogecoinID);
		GameRegistry.registerItem(dogecoin, "dogecoin");
		LanguageRegistry.addName(dogecoin, "Dogecoin");
		
		dogeLauncher = new DogeLauncher(dogeLauncherID);
		GameRegistry.registerItem(dogeLauncher, "dogeLauncher");
		LanguageRegistry.addName(dogeLauncher, "Doge Launcher");
		
		//Doge Tools
		dogePickaxe = new DogePickaxe(dogePickaxeID, dogeToolMat);
		GameRegistry.registerItem(dogePickaxe, "dogePickaxe");
		LanguageRegistry.addName(dogePickaxe, "such mine");
		
		dogeAxe = new DogeAxe(dogeAxeID, dogeToolMat);
		GameRegistry.registerItem(dogeAxe, "dogeAxe");
		LanguageRegistry.addName(dogeAxe, "many chop");
		
		dogeShovel = new DogeShovel(dogeShovelID, dogeToolMat);
		GameRegistry.registerItem(dogeShovel, "dogeShovel");
		LanguageRegistry.addName(dogeShovel, "much dig");
		
		dogeHoe = new DogeHoe(dogeHoeID, dogeToolMat);
		GameRegistry.registerItem(dogeHoe, "dogeHoe");
		LanguageRegistry.addName(dogeHoe, "very farm");
		
		dogeSword = new DogeSword(dogeSwordID, dogeToolMat);
		GameRegistry.registerItem(dogeSword, "dogeSword");
		LanguageRegistry.addName(dogeSword, "wow attack");
		
		//Doge Armour
		dogeArmourRenderID = proxy.addArmour("DogeArmour");
		dogeHelmet = new DogeHelmet(dogeHelmetID, dogeArmorMat, dogeArmourRenderID, 0);
		GameRegistry.registerItem(dogeHelmet, "dogeHelmet");
		LanguageRegistry.addName(dogeHelmet, "many protect face");
		
		dogeChestplate = new DogeChestplate(dogeChestplateID, dogeArmorMat, dogeArmourRenderID, 1);
		GameRegistry.registerItem(dogeChestplate, "dogeChestplate");
		LanguageRegistry.addName(dogeChestplate, "much chest armour");
		
		dogeLeggings = new DogeLeggings(dogeLeggingsID, dogeArmorMat, dogeArmourRenderID, 2);
		GameRegistry.registerItem(dogeLeggings, "dogeLeggings");
		LanguageRegistry.addName(dogeLeggings, "very leg defend");
		
		dogeBoots = new DogeBoots(dogeBootsID, dogeArmorMat, dogeArmourRenderID, 3);
		GameRegistry.registerItem(dogeBoots, "dogeBoots");
		LanguageRegistry.addName(dogeBoots, "wow shoes");
		
		
		//Blocks
		dogeBlock = new DogeBlock(dogeBlockID, Material.ground);
		GameRegistry.registerBlock(dogeBlock, "dogeBlock");
		LanguageRegistry.addName(dogeBlock, "Doge Block");
		
		
		//Entities
		
		EntityRegistry.registerModEntity(DogeProjectile.class, "dogeProjectile", EntityRegistry.findGlobalUniqueEntityId(), this, 128, 1, true);
		
		//Mobs
		registerMobEntity(DogeMob.class, "DogeMob", 0xeaeae9, 0xc99a03);
		LanguageRegistry.instance().addStringLocalization("entity.DogeMob.name", "Doge");
		
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		//Recipes
		GameRegistry.addRecipe(new ItemStack(dogecoin, 1), " G ", "GDG", " G ",
				'D', new ItemStack(Item.diamond), 'G', new ItemStack(Item.ingotGold));
		
		GameRegistry.addRecipe(new ItemStack(dogeBlock), "###", "###", "###",
				'#', new ItemStack(dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(dogeLauncher, 1), " D ", "DD ", " BD",
				'D', new ItemStack(dogecoin), 'B', new ItemStack(Block.stoneButton));
		
		GameRegistry.addShapelessRecipe(new ItemStack(dogecoin, 9), new ItemStack(dogeBlock));
		
		//Doge Tool Recipes
		GameRegistry.addRecipe(new ItemStack(dogePickaxe), "###", " S ", " S ",
				'#', new ItemStack(dogecoin), 'S', new ItemStack(Item.stick));
		
		GameRegistry.addRecipe(new ItemStack(dogeAxe), " ##", " S#", " S ",
				'#', new ItemStack(dogecoin), 'S', new ItemStack(Item.stick));
		
		GameRegistry.addRecipe(new ItemStack(dogeShovel), " # ", " S ", " S ",
				'#', new ItemStack(dogecoin), 'S', new ItemStack(Item.stick));
		
		GameRegistry.addRecipe(new ItemStack(dogeHoe), " ##", " S ", " S ",
				'#', new ItemStack(dogecoin), 'S', new ItemStack(Item.stick));
		
		GameRegistry.addRecipe(new ItemStack(dogeSword), " # ", " # ", " S ",
				'#', new ItemStack(dogecoin), 'S', new ItemStack(Item.stick));
		
		//Doge Armour Recipes
		GameRegistry.addRecipe(new ItemStack(dogeHelmet), "###", "# #",
				'#', new ItemStack(dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(dogeChestplate), "# #", "###", "###",
				'#', new ItemStack(dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(dogeLeggings), "###", "# #", "# #",
				'#', new ItemStack(dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(dogeBoots), "# #", "# #",
				'#', new ItemStack(dogecoin));
		
		proxy.registerRenderers();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
	
	public void registerMobEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor) {
		int id = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
		EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, bkEggColor, fgEggColor));
	}
	
	public void addSpawn(Class<? extends EntityLiving> entityClass, int spawnProb, int min, int max, BiomeGenBase[] biomes) {
		if (spawnProb > 0) {
			EntityRegistry.addSpawn(entityClass, spawnProb, min, max, EnumCreatureType.creature, biomes);
		}
	}
	
}