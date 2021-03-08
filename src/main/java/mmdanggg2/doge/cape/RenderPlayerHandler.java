/**
 * DeveloperCapes by Jadar
 * License: MIT License
 * (https://raw.github.com/jadar/DeveloperCapes/master/LICENSE)
 * version 4.0.0.x
 */
package mmdanggg2.doge.cape;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/** This is not the class you are looking for.
 * 
 * @author jadar */
public class RenderPlayerHandler {
	
	@SubscribeEvent
	public void renderPlayer(RenderLivingEvent.Pre<PlayerEntity, ?> event) {
		if (event.getEntity() instanceof AbstractClientPlayerEntity) {
			AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) event.getEntity();
			
			DogeCape cape = DevCapes.getInstance().getCape(player.getGameProfile().getName());
			if (cape == null)
				return;
			
			if (!cape.isTextureLoaded(player)) {
				cape.loadTexture(player);
			}
		}
	}
}