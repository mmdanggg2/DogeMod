package mmdanggg2.doge.client;
 
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import mmdanggg2.doge.CommonProxy;
import mmdanggg2.doge.Doge;
import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.entities.DogeProjectile;
import mmdanggg2.doge.renderer.DogeMobRender;
 
public class ClientProxy extends CommonProxy {
       
        @Override
        public void registerRenderers() {
        	
        	RenderingRegistry.registerEntityRenderingHandler(DogeMob.class, new DogeMobRender(new ModelWolf(), new ModelWolf(), 0.5f));
        	
        	RenderingRegistry.registerEntityRenderingHandler(DogeProjectile.class, new RenderSnowball(Doge.dogecoin));
        	
        }
        
        public static int addArmour(String armourName) {return RenderingRegistry.addNewArmourRendererPrefix(armourName);}
       
}