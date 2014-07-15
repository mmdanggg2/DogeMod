package mmdanggg2.doge;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
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
	
	public static EnumToolMaterial dogeToolMat = EnumHelper.addToolMaterial("Doge", 3, 600, 20.0F, 4.0F, 30);
	public static EnumArmorMaterial dogeArmorMat = EnumHelper.addArmorMaterial("Doge", 30, new int[] { 5, 10, 8, 5 }, 30);
	
	public static int dogeArmourRenderID = proxy.addArmour("DogeArmour");
	
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
	public static Block dogeBlock;
	
	public static Item dogecoin;
	
	public static Item dogeLauncher;
	
	
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
		
		DogeRegisterItems.register();

		DogeRegisterBlocks.register();

		DogeRegisterEntities.register();

		DogeRegisterRecipies.register();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
}