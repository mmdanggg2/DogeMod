package mmdanggg2.doge.client;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.client.gui.MiningRigScreen;
import mmdanggg2.doge.client.renderer.ShibaRender;
import mmdanggg2.doge.entity.DogeProjectile;
import mmdanggg2.doge.entity.ShibaEntity;
import mmdanggg2.doge.init.DogeContainerTypes;
import mmdanggg2.doge.init.DogeEntityTypes;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
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
		RenderingRegistry.registerEntityRenderingHandler(DogeEntityTypes.SHIBA.get(), ShibaRender::new);

		RenderingRegistry.registerEntityRenderingHandler(DogeEntityTypes.DOGECOIN.get(), new IRenderFactory<DogeProjectile>() {
			@Override
			public EntityRenderer<? super DogeProjectile> createRenderFor(EntityRendererManager manager) {
				return new SpriteRenderer<DogeProjectile>(manager, Minecraft.getInstance().getItemRenderer());
			}
		});

		// Register ContainerType Screens
		// ScreenManager.registerFactory is not safe to call during parallel mod loading so we queue it to run later
		event.enqueueWork(() -> {
			ScreenManager.registerFactory(DogeContainerTypes.MINING_RIG.get(), MiningRigScreen::new);
			DogeLogger.logDebug("Registered ContainerType Screens");
			
			GlobalEntityTypeAttributes.put(DogeEntityTypes.SHIBA.get(), ShibaEntity.getCustomAttributes().create());
		});
		
		//DevCapes capesInstance = DevCapes.getInstance();
		//capesInstance.registerConfig("http://www.mmdanggg2.co.uk/doge/capeConfig.json");
	}

}