package mmdanggg2.doge;

import mmdanggg2.doge.config.DogeConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Doge.ID)
public class Doge {
	public static final String ID = "doge";
	public static final String NAME = "Doge";
	public static final String VER = "0.6.0";
	
	public Doge() {
		ModLoadingContext context = ModLoadingContext.get();
		
		context.registerConfig(ModConfig.Type.SERVER, DogeConfig.SERVER_SPEC);
		
		//DogeLogger.logInfo("Registering Recipes");
		//DogeRegisterRecipes.register();
		
	}
	
	/*@EventHandler
	public void init(FMLInitializationEvent event) {
		
		proxy.regCape();
		new GUIHandler();
		
	}*/
}
