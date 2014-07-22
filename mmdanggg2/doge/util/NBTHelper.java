package mmdanggg2.doge.util;

import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {
	/**
	 * @param stackTag
	 *            The NBTTagCompund to use
	 * @param tag
	 *            The name of the tag to get
	 * @param def
	 *            The default value of the tag if it doesn't exist
	 * @return The value in the tag or the default if it didn't exist
	 */
	public static float getFloat(NBTTagCompound stackTag, String tag, float def) {
		if (stackTag != null) {
			if (stackTag.hasKey(tag)) {
				return stackTag.getFloat(tag);
			}
			else {
				stackTag.setFloat(tag, def);
				return def;
			}
		}
		return def;
	}
	
	/**
	 * @param stackTag
	 *            The NBTTagCompund to use
	 * @param tag
	 *            The name of the tag to get
	 * @param def
	 *            The default value of the tag if it doesn't exist
	 * @return The value in the tag or the default if it didn't exist
	 */
	public static int getInt(NBTTagCompound stackTag, String tag, int def) {
		if (stackTag != null) {
			if (stackTag.hasKey(tag)) {
				return stackTag.getInteger(tag);
			}
			else {
				stackTag.setInteger(tag, def);
				return def;
			}
		}
		return def;
	}
}
