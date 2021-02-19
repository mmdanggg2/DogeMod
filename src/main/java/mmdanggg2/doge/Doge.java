package mmdanggg2.doge;

import mmdanggg2.doge.config.DogeConfig;
import mmdanggg2.doge.init.DogeBlocks;
import mmdanggg2.doge.init.DogeContainerTypes;
import mmdanggg2.doge.init.DogeItems;
import mmdanggg2.doge.init.DogeTileEntityTypes;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Doge.ID)
public class Doge {
	public static final String ID = "doge";
	public static final String NAME = "Doge";
	public static final String VER = "0.6.0";
	
	public Doge() {
		ModLoadingContext context = ModLoadingContext.get();
		FMLJavaModLoadingContext jModContext = FMLJavaModLoadingContext.get();
		
		context.registerConfig(ModConfig.Type.SERVER, DogeConfig.SERVER_SPEC);
		
		DogeItems.ITEMS.register(jModContext.getModEventBus());
		DogeBlocks.BLOCKS.register(jModContext.getModEventBus());
		DogeTileEntityTypes.TILE_ENTITY_TYPES.register(jModContext.getModEventBus());
		DogeContainerTypes.CONTAINERS.register(jModContext.getModEventBus());
		
		//DogeLogger.logInfo("Registering Recipes");
		//DogeRegisterRecipes.register();
		
	}
	
	/*@EventHandler
	public void init(FMLInitializationEvent event) {
		
		proxy.regCape();
		new GUIHandler();
		
	}*/
}
