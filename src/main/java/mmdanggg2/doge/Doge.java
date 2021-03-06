package mmdanggg2.doge;

import mmdanggg2.doge.blocks.DogeBlock;
import mmdanggg2.doge.blocks.MiningRig;
import mmdanggg2.doge.client.interfaces.GUIHandler;
import mmdanggg2.doge.creativetab.DogeCreativeTab;
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
import mmdanggg2.doge.items.GPU;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

@Mod(modid = DogeInfo.ID, name = DogeInfo.NAME, version = DogeInfo.VER, guiFactory = "mmdanggg2.doge.client.DogeConfigGUIFactory")
public class Doge {
	// The instance of your mod that Forge uses.
	@Instance(DogeInfo.ID)
	public static Doge instance;
	public static Configuration config;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = DogeInfo.CLIENTPROXY, serverSide = DogeInfo.COMMONPROXY)
	public static CommonProxy proxy;

	//Inits
	public static ToolMaterial dogeToolMat;
	public static ArmorMaterial dogeArmorMat;
	
	// Doge Tools
	public static DogePickaxe dogePickaxe;
	public static DogeAxe dogeAxe;
	public static DogeShovel dogeShovel;
	public static DogeHoe dogeHoe;
	public static DogeSword dogeSword;

	// Doge Armour
	public static DogeHelmet dogeHelmet;
	public static DogeChestplate dogeChestplate;
	public static DogeLeggings dogeLeggings;
	public static DogeBoots dogeBoots;

	// Other
	public static DogeBlock dogeBlock;

	public static Dogecoin dogecoin;

	public static DogeLauncher dogeLauncher;
	
	public static GPU gpu;
	
	public static MiningRig miningRig;
	
	// Creative Tab
	public static CreativeTabs dogeTab;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if (config == null) {
			config = new Configuration(event.getSuggestedConfigurationFile());
		}
		
		DogeLogger.logger = event.getModLog();
		
		DogeLogger.logInfo("Loading Config");
		// loading the configuration from its file
		config.load();
		updateConfig();
		
		int tDur = DogeInfo.toolDurability;
		float tSpd = DogeInfo.toolSpeed;
		float tDmg = DogeInfo.toolDamage;
		
		dogeToolMat = EnumHelper.addToolMaterial("Doge", 3, tDur, tSpd, tDmg, 30);
		dogeArmorMat = EnumHelper.addArmorMaterial("Doge", "doge", 30, new int[]{5, 10, 8, 5}, 30, null, 4);
		
		dogeTab = new DogeCreativeTab("doge_tab");

		MinecraftForge.EVENT_BUS.register(instance);
		
		//DogeLogger.logInfo("Registering Recipes");
		//DogeRegisterRecipes.register();
		
	}
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		DogeLogger.logInfo("Registering Blocks");
		DogeRegisterBlocks.register(event.getRegistry());
		proxy.registerBlockRenderers();
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		DogeLogger.logInfo("Registering Items");
		DogeRegisterItems.register(event.getRegistry());
		proxy.registerItemRenderers();
	}
	
	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		DogeLogger.logInfo("Registering Entities");
		DogeRegisterEntities.register(event.getRegistry());
		proxy.registerEntityRenderers();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		proxy.regCape();
		new GUIHandler();
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
	
	public static void updateConfig() {
		
		DogeInfo.toolDurability = config.get("doge_tools", "ToolDurability", 780, "How many uses the tools have (Default 780)").getInt(780);
		DogeInfo.toolSpeed = (float) config.get("doge_tools", "ToolSpeed", 20.0F, "How fast the tools mine their respective blocks (Default 20.0)").getDouble(20.0F);
		DogeInfo.toolDamage = (float) config.get("doge_tools", "ToolDamage", 6.0F, "How much damage the tools do (Default 6.0)").getDouble(6.0F);
		
		DogeInfo.rigChance = config.get("mining_rig", "RigChance", 5, "How likely a GPU is to get a Dogecoin, lower is more likely. There is a 1 in x chance (Default 5)").getInt(5);
		DogeInfo.rigSpeed = config.get("mining_rig", "RigSpeed", 100, "How fast the rig uses the GPUs, lower is faster (Default 100)").getInt(100);
		
		DogeInfo.gpuChance = config.get("gpu", "GPUChance", 11, "How likely a GPU is to get a Dogecoin, lower is more likely. There is a 1 in x chance per block. (Default 11)").getInt(11);
		DogeInfo.gpuSpeedStart = (float) config.get("gpu", "GPUSpeedStart", 1.0F, "The mining speed of the GPU when fully cooled (Default 1.0)").getDouble(1.0F);
		DogeInfo.gpuSpeedStep = (float) config.get("gpu", "GPUSpeedStep", 2.0F, "How much speed the GPU gains per mine (Default 2.0)").getDouble(2.0F);
		DogeInfo.gpuCoolRate = config.get("gpu", "GPUCoolRate", 30, "How quickly the GPU cools down when not in hand, lower is faster (Default 30)").getInt(30);
		
		DogeInfo.dogecoinConvertList = config.get("dogecoin", "DogecoinConvertWhitelist", new String[] {
				"EntityPig",
				"EntityWolf",
				"EntityChicken",
				"EntityCow",
				"EntityOcelot",
				"EntitySheep",
				"EntitySquid",
				"EntityCreeper",
				"EntitySnowman"}, "This is a list of Java classes that are able to be converted into Shibe's by a coin").getStringList();
		
		DogeInfo.shibeSpawnBiomes = config.get("shibe", "ShibeSpawnBiomes", new int[] { 1, 4 }, "This is a list of biome id's that shibes can spawn in (Default 1, 4)").getIntList();
		DogeInfo.shibeSpawnChance = config.get("shibe", "ShibeSpawnChance", 4, "How likely the shibes spawn in the biomes, lower is less likely (Default 4)").getInt(4);
		DogeInfo.shibeSpawnMinSize = config.get("shibe", "ShibeSpawnMinSize", 1, "Minimum ammount of shibes that spawn when they do (Default 1)").getInt(1);
		DogeInfo.shibeSpawnMaxSize = config.get("shibe", "ShibeSpawnMaxSize", 6, "Maximum ammount of shibes that spawn when they do (Default 6)").getInt(6);
		DogeInfo.shibeAtkDamage = config.get("shibe", "ShibeAttackDamage", 8, "Ammount of damage a shibe will do when tame, halved when wild (Default 8)").getInt(8);
		
		DogeInfo.debug = config.get("debug", "DebugOutput", false, "Show debug output in log (Default false)").getBoolean(false);
		
		// saving the configuration to its file
		if (config.hasChanged()){
			config.save();
		}
	}

	@SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.getModID().equals(DogeInfo.ID))
        	DogeLogger.logInfo("Reloading Config");
            updateConfig();
    }
}
