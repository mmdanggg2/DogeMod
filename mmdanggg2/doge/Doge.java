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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=BasicInfo.ID, name=BasicInfo.NAME, version=BasicInfo.VER)
public class Doge {
	// The instance of your mod that Forge uses.
	@Instance(BasicInfo.NAME)
	public static Doge instance;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = BasicInfo.CLIENTPROXY + "ClientProxy", serverSide = BasicInfo.COMMONPROXY + "CommonProxy")
	public static CommonProxy proxy;

	//Inits
	public static int dogeArmourRenderID;
	
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

	// Settings
	public static int toolDurability;
	public static float toolSpeed;
	public static float toolDamage;
	
	// Creative Tab
	public static CreativeTabs dogeTab;

	public static boolean debug;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		DogeLogger.logInfo("Loading Config");
		// loading the configuration from its file
		config.load();
		
		toolDurability = config.get("Doge_Tools", "ToolDurability", 780, "How many uses the tools have (Default 780)").getInt(780);
		toolSpeed = (float) config.get("Doge_Tools", "ToolSpeed", 20.0F, "How fast the tools mine their respective blocks (Default 20.0)").getDouble(20.0F);
		toolDamage = (float) config.get("Doge_Tools", "ToolDamage", 6.0F, "How much damage the tools do (Default 6.0)").getDouble(6.0F);
		
		debug = config.get("Debug", "DebugOutput", false, "Show debug output in log (Default false)").getBoolean(false);
		
		// saving the configuration to its file
		config.save();
		
		dogeToolMat = EnumHelper.addToolMaterial("Doge", 3, toolDurability, toolSpeed, toolDamage, 30);
		dogeArmorMat = EnumHelper.addArmorMaterial("Doge", 30, new int[] { 5, 10, 8, 5 }, 30);
		
		dogeArmourRenderID = proxy.addArmour("DogeArmour");
		
		dogeTab = new DogeCreativeTab("dogeTab");

		DogeLogger.logInfo("Registering Items");
		DogeRegisterItems.register();
		
		DogeLogger.logInfo("Registering Blocks");
		DogeRegisterBlocks.register();
		
		DogeLogger.logInfo("Registering Entities");
		DogeRegisterEntities.register();
		
		DogeLogger.logInfo("Registering Recipies");
		DogeRegisterRecipies.register();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		proxy.registerRenderers();
		proxy.regCape();
		new GUIHandler();

	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
}