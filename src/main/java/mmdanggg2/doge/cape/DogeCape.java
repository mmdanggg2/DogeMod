/**
 * DeveloperCapes by Jadar
 * License: MIT License
 * (https://raw.github.com/jadar/DeveloperCapes/master/LICENSE)
 * version 4.0.0.x
 */
package mmdanggg2.doge.cape;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.util.ResourceLocation;

/**
 * Default Cape implementation
 * 
 * @author jadar
 */
public class DogeCape {
	
	public String name;
	public ResourceLocation location;
	protected Texture texture;
	
	public DogeCape(String name, URL url) {
		this.setName(name);
		this.setURL(url);
	}
	
	public DogeCape(String name) {
		this(name, null);
	}
	
	@SuppressWarnings("resource")
	public void loadTexture(AbstractClientPlayerEntity player) {
		
		// mmdanggg2: using reflection to modify the private locationCape, hacky but it works.
		try {
			NetworkPlayerInfo npi = Minecraft.getInstance().getConnection().getPlayerInfo(player.getUniqueID());
			Field plrTxtrsF;
			try {
				plrTxtrsF = NetworkPlayerInfo.class.getDeclaredField("playerTextures");
			} catch (NoSuchFieldException e) {
				plrTxtrsF = NetworkPlayerInfo.class.getDeclaredField("field_187107_a");
			}
			plrTxtrsF.setAccessible(true);
			Map<Type, ResourceLocation> plrTxtrs = (Map<Type, ResourceLocation>) plrTxtrsF.get(npi);
			plrTxtrs.put(Type.CAPE, location);
			plrTxtrsF.setAccessible(false);
			
		} catch (Exception e) {
			e.printStackTrace();
			DogeLogger.logger.error("Setting cape ResourceLocation failed!");
		}
		
		Minecraft.getInstance().textureManager.loadTexture(location, texture);
		
	}
	
	public boolean isTextureLoaded(AbstractClientPlayerEntity player) {
		ResourceLocation cape = player.getLocationCape();
		return cape != null;
	}
	
	public void setURL(URL url) {
		if (url == null) {
			this.texture = null;
			return;
		}
		this.texture = new DownloadingCapeTexture(url, location);
	}
	
	public void setName(String name) {
		this.name = name;
		this.location = new ResourceLocation("devcapes:" + name.toLowerCase());
	}
}