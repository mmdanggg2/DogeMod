package mmdanggg2.doge.client;

import com.jadarstudios.developercapes.DevCapes;

import mmdanggg2.doge.CommonProxy;
import mmdanggg2.doge.Doge;
import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.entities.DogeProjectile;
import mmdanggg2.doge.renderer.DogeMobRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(DogeMob.class, new IRenderFactory<DogeMob>() {
			@Override
			public Render<? super DogeMob> createRenderFor(RenderManager manager) {
				return new DogeMobRender(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(DogeProjectile.class, new IRenderFactory<DogeProjectile>() {
			@Override
			public Render<? super DogeProjectile> createRenderFor(RenderManager manager) {
				return new RenderSnowball<DogeProjectile>(manager, Doge.dogecoin, Minecraft.getMinecraft().getRenderItem());
			}
		});
	}
	
	@Override
	public void registerItemRenderers() {
		ModelLoader.setCustomModelResourceLocation(Doge.dogecoin, 0, new ModelResourceLocation(Doge.dogecoin.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Doge.dogeLauncher, 0, new ModelResourceLocation(Doge.dogeLauncher.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Doge.gpu, 0, new ModelResourceLocation(Doge.gpu.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Doge.dogePickaxe, 0, new ModelResourceLocation(Doge.dogePickaxe.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Doge.dogeAxe, 0, new ModelResourceLocation(Doge.dogeAxe.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Doge.dogeShovel, 0, new ModelResourceLocation(Doge.dogeShovel.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Doge.dogeHoe, 0, new ModelResourceLocation(Doge.dogeHoe.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Doge.dogeSword, 0, new ModelResourceLocation(Doge.dogeSword.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Doge.dogeHelmet, 0, new ModelResourceLocation(Doge.dogeHelmet.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Doge.dogeChestplate, 0, new ModelResourceLocation(Doge.dogeChestplate.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Doge.dogeLeggings, 0, new ModelResourceLocation(Doge.dogeLeggings.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Doge.dogeBoots, 0, new ModelResourceLocation(Doge.dogeBoots.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Doge.dogeBlock), 0, new ModelResourceLocation(Doge.dogeBlock.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Doge.miningRig), 0, new ModelResourceLocation(Doge.miningRig.getRegistryName(), "inventory"));
	}
	
	@Override
	public void registerBlockRenderers() {
	}
	
	@Override
	public void regCape() {
		DevCapes capesInstance = DevCapes.getInstance();
		capesInstance.registerConfig("http://www.mmdanggg2.co.uk/doge/capeConfig.json");
	}

}