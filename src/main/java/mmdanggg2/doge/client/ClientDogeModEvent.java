package mmdanggg2.doge.client;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.entities.DogeProjectile;
import mmdanggg2.doge.init.DogeEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Doge.ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientDogeModEvent {

	@SubscribeEvent
	public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
		/*RenderingRegistry.registerEntityRenderingHandler(DogeMob.class, new IRenderFactory<DogeMob>() {
			@Override
			public Render<? super DogeMob> createRenderFor(RenderManager manager) {
				return new DogeMobRender(manager);
			}
		});*/
		
		RenderingRegistry.registerEntityRenderingHandler(DogeEntityTypes.DOGECOIN.get(), new IRenderFactory<DogeProjectile>() {
			@Override
			public EntityRenderer<? super DogeProjectile> createRenderFor(EntityRendererManager manager) {
				return new SpriteRenderer<DogeProjectile>(manager, Minecraft.getInstance().getItemRenderer());
			}
		});
		
		//DevCapes capesInstance = DevCapes.getInstance();
		//capesInstance.registerConfig("http://www.mmdanggg2.co.uk/doge/capeConfig.json");
	}

}