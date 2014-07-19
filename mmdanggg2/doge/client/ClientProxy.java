package mmdanggg2.doge.client;

import mmdanggg2.doge.CommonProxy;
import mmdanggg2.doge.Doge;
import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.entities.DogeProjectile;
import mmdanggg2.doge.renderer.DogeMobRender;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderSnowball;

import com.jadarstudios.developercapes.DevCapes;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers() {
		
		RenderingRegistry.registerEntityRenderingHandler(DogeMob.class, new DogeMobRender(new ModelWolf(), new ModelWolf(), 0.5f));
		
		RenderingRegistry.registerEntityRenderingHandler(DogeProjectile.class, new RenderSnowball(Doge.dogecoin));
		
	}
	
	@Override
	public int addArmour(String armourName) {
		return RenderingRegistry.addNewArmourRendererPrefix(armourName);
	}
	
	@Override
	public void regCape() {
		DevCapes capesInstance = DevCapes.getInstance();
		capesInstance.addUser("mmdanggg2", "http://www.mmdanggg2.co.uk/doge/mmdanggg2Cape.png");
		capesInstance.addUser("Oscip", "http://www.mmdanggg2.co.uk/doge/OscipCape.png");
	}

}