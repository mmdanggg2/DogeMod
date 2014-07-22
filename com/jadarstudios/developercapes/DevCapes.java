/**
 * DeveloperCapes by Jadar
 * License: MIT License
 * (https://raw.github.com/jadar/DeveloperCapes/master/LICENSE)
 * version 3.3.0.0
 */
package com.jadarstudios.developercapes;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This library adds capes for people you specify
 * 
 * @author Jadar
 */
@SideOnly(Side.CLIENT)
public class DevCapes
{
	
	private static DevCapes instance;
	
	/**
	 * Gets the DevCapes instance
	 */
	public static DevCapes getInstance()
	{
		if (instance == null)
			instance = new DevCapes();
		return instance;
	}
	
	// name->group
	private HashMap<String, IUser> users;
	
	/**
	 * Object constructor.
	 */
	private DevCapes()
	{
		this.users = new HashMap<String, IUser>();
	}
	
	/**
	 * @param username
	 *            The name of the user that you want to give a cape to
	 * @param capeUrl
	 *            The URL as a String of the cape that you wish to assign to the
	 *            user
	 */
	public void addUser(final String username, final String capeUrl)
	{
		IUser user = this.users.get(username);
		if (user == null)
		{
			user = new DefaultUser(username, capeUrl);
			this.users.put(username, user);
			this.loadCape(username);
		}
	}
	
	/**
	 * @param username
	 *            The name of the user whose cape you wish to load
	 * @return true of the cape was loaded properly
	 */
	public boolean loadCape(final String username)
	{
		IUser user = this.users.get(username);
		return Minecraft.getMinecraft().renderEngine.loadTexture(user.getResource(), user.getTexture());
	}
}