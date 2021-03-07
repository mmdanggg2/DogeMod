package mmdanggg2.doge.cape;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DownloadingCapeTexture extends SimpleTexture {
	private static final Logger LOGGER = LogManager.getLogger();
	private final URL imageUrl;
	@Nullable
	private CompletableFuture<?> future;
	
	public DownloadingCapeTexture(URL url, ResourceLocation textureResourceLocation) {
		super(textureResourceLocation);
		this.imageUrl = url;
	}
	
	private void upload(NativeImage imageIn) {
		TextureUtil.prepareImage(this.getGlTextureId(), imageIn.getWidth(), imageIn.getHeight());
		imageIn.uploadTextureSub(0, 0, 0, true);
	}
	
	public void loadTexture(IResourceManager manager) throws IOException {
		if (this.future == null) {
			this.future = CompletableFuture.runAsync(() -> {
				HttpURLConnection httpurlconnection = null;
				LOGGER.debug("Downloading http texture from {}", this.imageUrl);
				
				try {
					httpurlconnection = (HttpURLConnection) this.imageUrl.openConnection(Minecraft.getInstance().getProxy());
					httpurlconnection.setDoInput(true);
					httpurlconnection.setDoOutput(false);
					httpurlconnection.connect();
					if (httpurlconnection.getResponseCode() / 100 == 2) {
						NativeImage nativeImage = NativeImage.read(httpurlconnection.getInputStream());
						if (nativeImage != null) {
							Minecraft.getInstance().execute(() -> {
								if (!RenderSystem.isOnRenderThread()) {
									RenderSystem.recordRenderCall(() -> {
										this.upload(nativeImage);
									});
								} else {
									this.upload(nativeImage);
								}
							});
						}
						return;
					}
				} catch (Exception exception) {
					LOGGER.error("Couldn't download http texture", (Throwable) exception);
					return;
				} finally {
					if (httpurlconnection != null) {
						httpurlconnection.disconnect();
					}
					
				}
				
			}, Util.getServerExecutor());
		}
	}
}
