package mmdanggg2.doge.util;

import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {
	/**
	 * @param tagComp
	 *            The NBTTagCompund to use
	 * @param tag
	 *            The name of the tag to get
	 * @param def
	 *            The default value of the tag if it doesn't exist
	 * @return The value in the tag or the default if it didn't exist
	 */
	public static float getFloat(NBTTagCompound tagComp, String tag, float def) {
		if (tagComp != null) {
			if (tagComp.hasKey(tag)) {
				return tagComp.getFloat(tag);
			}
			else {
				tagComp.setFloat(tag, def);
				return def;
			}
		}
		return def;
	}
	
	/**
	 * @param tagComp
	 *            The NBTTagCompund to use
	 * @param tag
	 *            The name of the tag to get
	 * @param def
	 *            The default value of the tag if it doesn't exist
	 * @return The value in the tag or the default if it didn't exist
	 */
	public static int getInt(NBTTagCompound tagComp, String tag, int def) {
		if (tagComp != null) {
			if (tagComp.hasKey(tag)) {
				return tagComp.getInteger(tag);
			}
			else {
				tagComp.setInteger(tag, def);
				return def;
			}
		}
		return def;
	}
}
