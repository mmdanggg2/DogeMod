package mmdanggg2.doge.client;
 
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelWolf;
import net.minecraftforge.client.MinecraftForgeClient;
import mmdanggg2.doge.CommonProxy;
import mmdanggg2.doge.Doge;
import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.entities.DogeProjectile;
import mmdanggg2.doge.renderer.DogeMobRender;
import mmdanggg2.doge.renderer.DogeProjectileRender;
 
public class ClientProxy extends CommonProxy {
       
        @Override
        public void registerRenderers() {
        	
        	RenderingRegistry.addNewArmourRendererPrefix("DogeArmour");
        	
        	RenderingRegistry.registerEntityRenderingHandler(DogeMob.class, new DogeMobRender(new ModelWolf(), new ModelWolf(), 0.5f));
        	
        	RenderingRegistry.registerEntityRenderingHandler(DogeProjectile.class, new DogeProjectileRender(Doge.dogecoin));
        	
        }
       
}