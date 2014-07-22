package mmdanggg2.doge;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
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
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid=BasicInfo.ID, name=BasicInfo.NAME, version=BasicInfo.VER)
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Doge {
	
	// The instance of your mod that Forge uses.
	@Instance(BasicInfo.NAME)
	public static Doge instance;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = BasicInfo.CLIENTPROXY + "ClientProxy", serverSide = BasicInfo.COMMONPROXY + "CommonProxy")
	public static CommonProxy proxy;
	
	// Inits
	
	public static int dogecoinID;
	public static int dogeLauncherID;
	
	public static int dogePickaxeID;
	public static int dogeAxeID;
	public static int dogeShovelID;
	public static int dogeHoeID;
	public static int dogeSwordID;
	
	public static int dogeHelmetID;
	public static int dogeChestplateID;
	public static int dogeLeggingsID;
	public static int dogeBootsID;
	
	public static int dogeBlockID;
	public static int dogeArmourRenderID;
	
	public static ToolMaterial dogeToolMat;
	public static ArmorMaterial dogeArmorMat;
	
	// Doge Tools
	public static Item dogePickaxe;
	public static Item dogeAxe;
	public static Item dogeShovel;
	public static Item dogeHoe;
	public static Item dogeSword;
	
	// Doge Armour
	public static Item dogeHelmet;
	public static Item dogeChestplate;
	public static Item dogeLeggings;
	public static Item dogeBoots;
	
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

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		DogeLogger.logInfo("Loading Config");
		// loading the configuration from its file
		config.load();
		
		toolDurability = config.get("Doge_Tools", "ToolDurability", 780, "How many uses the tools have (Default 780)").getInt(780);
		toolSpeed = (float) config.get("Doge_Tools", "ToolSpeed", 20.0F, "How fast the tools mine their respective blocks (Default 20.0)").getDouble(20.0F);
		toolDamage = (float) config.get("Doge_Tools", "ToolDamage", 6.0F, "How much damage the tools do (Default 6.0)").getDouble(6.0F);
		
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