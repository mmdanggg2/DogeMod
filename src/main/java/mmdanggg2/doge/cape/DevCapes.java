/**
 * DeveloperCapes by Jadar
 * License: MIT License
 * (https://raw.github.com/jadar/DeveloperCapes/master/LICENSE)
 * version 4.0.0.x
 */
package mmdanggg2.doge.cape;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import mmdanggg2.doge.util.DogeLogger;
import net.minecraftforge.common.MinecraftForge;

/** DeveloperCapes is a library for Minecraft. It allows developers to quickly add capes for players they specify. DevCapes uses Minecraft
 * Forge.
 *
 * @author jadar */
public class DevCapes {
	private static DevCapes instance;
	
	private HashMap<String, DogeCape> users = new HashMap<String, DogeCape>();
	
	protected DevCapes() {
		MinecraftForge.EVENT_BUS.register(new RenderPlayerHandler());
	}
	
	public static DevCapes getInstance() {
		if (instance == null) {
			instance = new DevCapes();
		}
		return instance;
	}
	
	/** InputStream.close() needs to be called on this after you're done!
	 * 
	 * @return {@link InputStream} for the {@link URL} */
	public InputStream getStreamForURL(URL url) {
		InputStream is = null;
		try {
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", System.getProperty("java.version"));
			connection.connect();
			
			is = connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}
	
	/** Registers a config with DevCapes.
	 *
	 * @param jsonUrl The URL as a String that links to the Json file that you want
	 *                to add
	 * @return If the config was registered */
	public boolean registerConfig(String jsonUrl) {
		try {
			URL url = new URL(jsonUrl);
			return this.registerConfig(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/** Registers a config with DevCapes and returns the ID of the config.
	 *
	 * @param jsonUrl A {@link URL} that links to the Json file that you want to add
	 * @return If the config was registered */
	public boolean registerConfig(URL jsonUrl) {
		InputStream is = this.getStreamForURL(jsonUrl);
		
		if (is == null) {
			DogeLogger.logger.error(String.format("Unable to establish a connection to the server, %s", jsonUrl.getHost()));
			return false;
		}

		InputStreamReader isr = new InputStreamReader(is);
		
		try {
			Map<String, Object> entries = new Gson().fromJson(isr, Map.class);
			
			for (Map.Entry<String, Object> entry : entries.entrySet()) {
				final String nodeName = entry.getKey();
				final Object obj = entry.getValue();
				if (obj instanceof String) {
					parseUser(nodeName, (String) obj);
				}
			}
		} catch (JsonSyntaxException e) {
			DogeLogger.logger.error("CapeConfig could not be parsed because:");
			e.printStackTrace();
		}
		
		silentClose(is);
		
		return true;
	}
	
	protected void parseUser(String user, String url) {
		try {
			DogeCape cape = new DogeCape(user, new URL(url));
			if (cape != null) {
				users.put(user, cape);
			}
		} catch (MalformedURLException e) {
			DogeLogger.logger.error(String.format("Are you crazy?? \"%s\" is not a valid URL!", url));
			e.printStackTrace();
		}
	}
	
	private static void silentClose(InputStream is) {
		try {
			is.close();
		} catch (IOException ignored) {
		}
	}
	
	public @Nullable DogeCape getCape(String username) {
		return users.get(username);
	}
}