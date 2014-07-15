package mmdanggg2.doge;

import mmdanggg2.doge.blocks.DogeBlock;
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
	public static int dogeArmourRenderID = proxy.addArmour("DogeArmour");
	
	public static ToolMaterial dogeToolMat = EnumHelper.addToolMaterial("Doge", 3, 600, 20.0F, 4.0F, 30);
	public static ArmorMaterial dogeArmorMat = EnumHelper.addArmorMaterial("Doge", 30, new int[] { 5, 10, 8, 5 }, 30);

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

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		// loading the configuration from its file
		config.load();
		
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