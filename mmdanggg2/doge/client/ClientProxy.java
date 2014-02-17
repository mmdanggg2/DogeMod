package mmdanggg2.doge.client;
 
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelWolf;
import net.minecraftforge.client.MinecraftForgeClient;
import mmdanggg2.doge.CommonProxy;
import mmdanggg2.doge.entities.DogeMob;
 
public class ClientProxy extends CommonProxy {
       
        @Override
        public void registerRenderers() {
        	
        	RenderingRegistry.registerEntityRenderingHandler(DogeMob.class, new DogeMob.RenderDoge(new ModelWolf(), new ModelWolf(), 0.5f));
        	
        }
       
}