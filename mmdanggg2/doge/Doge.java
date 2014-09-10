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
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
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

@Mod(modid=DogeInfo.ID, name=DogeInfo.NAME, version=DogeInfo.VER)
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Doge {
	
	// The instance of your mod that Forge uses.
	@Instance(DogeInfo.NAME)
	public static Doge instance;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = DogeInfo.CLIENTPROXY, serverSide = DogeInfo.COMMONPROXY)
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
	public static int miningRigID;
	public static int gpuID;
	
	public static int dogeArmourRenderID;
	
	public static EnumToolMaterial dogeToolMat;
	public static EnumArmorMaterial dogeArmorMat;
	
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
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		DogeLogger.logInfo("Loading Config");
		// loading the configuration from its file
		config.load();
		
		int tDur = DogeInfo.toolDurability = config.get("Doge_Tools", "ToolDurability", 780, "How many uses the tools have (Default 780)").getInt(780);
		float tSpd = DogeInfo.toolSpeed = (float) config.get("Doge_Tools", "ToolSpeed", 20.0F, "How fast the tools mine their respective blocks (Default 20.0)").getDouble(20.0F);
		float tDmg = DogeInfo.toolDamage = (float) config.get("Doge_Tools", "ToolDamage", 6.0F, "How much damage the tools do (Default 6.0)").getDouble(6.0F);
		
		DogeInfo.rigChance = config.get("Mining_Rig", "RigChance", 5, "How likely a GPU is to get a Dogecoin, lower is more likely. There is a 1 in x chance. (Default 5)").getInt(5);
		DogeInfo.rigSpeed = config.get("Mining_Rig", "RigSpeed", 100, "How fast the rig uses the GPUs, lower is faster (Default 100)").getInt(100);
		
		DogeInfo.gpuChance = config.get("GPU", "GPUChance", 11, "How likely a GPU is to get a Dogecoin, lower is more likely. There is a 1 in x chance per block. (Default 11)").getInt(11);
		DogeInfo.gpuSpeedStart = (float) config.get("GPU", "GPUSpeedStart", 1.0F, "The mining speed of the GPU when fully cooled (Default 1.0)").getDouble(1.0F);
		DogeInfo.gpuSpeedStep = (float) config.get("GPU", "GPUSpeedStep", 2.0F, "How much speed the GPU gains per mine (Default 2.0)").getDouble(2.0F);
		DogeInfo.gpuCoolRate = config.get("GPU", "GPUCoolRate", 30, "How quickly the GPU cools down when not in hand, lower is faster (Default 30)").getInt(30);
		
		DogeInfo.debug = config.get("Debug", "DebugOutput", false, "Show debug output in log (Default false)").getBoolean(false);
		
		//Items
		dogecoinID = config.getItem("dogecoin", 5000).getInt();
		dogeLauncherID = config.getItem("dogeLauncher", 5010).getInt();
		gpuID = config.getItem("gpu", 5011).getInt();
		
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
		miningRigID = config.getBlock("miningRig", 501).getInt();
		
		// saving the configuration to its file
		config.save();
		
		dogeToolMat = EnumHelper.addToolMaterial("Doge", 3, tDur, tSpd, tDmg, 30);
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
		
		dogeToolMat.customCraftingMaterial = dogecoin;
		dogeArmorMat.customCraftingMaterial = dogecoin;
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