package mmdanggg2.doge.client;

import mmdanggg2.doge.CommonProxy;
import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.entities.DogeProjectile;
import mmdanggg2.doge.renderer.DogeMobRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

//import com.jadarstudios.developercapes.DevCapes;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers() {
		
		RenderManager rendMan = Minecraft.getMinecraft().getRenderManager();
		RenderItem rendItem = Minecraft.getMinecraft().getRenderItem();
		
		RenderingRegistry.registerEntityRenderingHandler(DogeMob.class, new DogeMobRender(rendMan, new ModelWolf(), 0.5f));
		
		RenderingRegistry.registerEntityRenderingHandler(DogeProjectile.class, new RenderSnowball(rendMan, Doge.dogecoin, rendItem));
		
		ItemModelMesher itemMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		itemMesher.register(Doge.dogecoin, 0, new ModelResourceLocation(DogeInfo.ID + ":dogecoin", "inventory"));
		itemMesher.register(Doge.dogeLauncher, 0, new ModelResourceLocation(DogeInfo.ID + ":dogeLauncher", "inventory"));
		itemMesher.register(Doge.gpu, 0, new ModelResourceLocation(DogeInfo.ID + ":gpu", "inventory"));
		itemMesher.register(Doge.dogePickaxe, 0, new ModelResourceLocation(DogeInfo.ID + ":dogePickaxe", "inventory"));
		itemMesher.register(Doge.dogeAxe, 0, new ModelResourceLocation(DogeInfo.ID + ":dogeAxe", "inventory"));
		itemMesher.register(Doge.dogeShovel, 0, new ModelResourceLocation(DogeInfo.ID + ":dogeShovel", "inventory"));
		itemMesher.register(Doge.dogeHoe, 0, new ModelResourceLocation(DogeInfo.ID + ":dogeHoe", "inventory"));
		itemMesher.register(Doge.dogeSword, 0, new ModelResourceLocation(DogeInfo.ID + ":dogeSword", "inventory"));
		itemMesher.register(Doge.dogeHelmet, 0, new ModelResourceLocation(DogeInfo.ID + ":dogeHelmet", "inventory"));
		itemMesher.register(Doge.dogeChestplate, 0, new ModelResourceLocation(DogeInfo.ID + ":dogeChestplate", "inventory"));
		itemMesher.register(Doge.dogeLeggings, 0, new ModelResourceLocation(DogeInfo.ID + ":dogeLeggings", "inventory"));
		itemMesher.register(Doge.dogeBoots, 0, new ModelResourceLocation(DogeInfo.ID + ":dogeBoots", "inventory"));
		
		itemMesher.register(Item.getItemFromBlock(Doge.dogeBlock), 0, new ModelResourceLocation(DogeInfo.ID + ":dogeBlock", "inventory"));
		itemMesher.register(Item.getItemFromBlock(Doge.miningRig), 0, new ModelResourceLocation(DogeInfo.ID + ":miningRig", "inventory"));
		itemMesher.register(Item.getItemFromBlock(Doge.miningRigOn), 0, new ModelResourceLocation(DogeInfo.ID + ":miningRigOn", "inventory"));
	}
	
	@Override
	public int addArmour(String armourName) {
		return 5;//FIXME RenderingRegistry.addNewArmourRendererPrefix(armourName);
	}
	
	@Override
	public void regCape() {
		//FIXME
		//DevCapes capesInstance = DevCapes.getInstance();
		//capesInstance.registerConfig("http://www.mmdanggg2.co.uk/doge/capeConfig.json");
	}

}